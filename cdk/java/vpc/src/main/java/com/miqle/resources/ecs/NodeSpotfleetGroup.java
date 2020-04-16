package com.miqle.resources.ecs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.miqle.resources.vpc.PrivateSecurityGroup;
import com.miqle.resources.vpc.VpcResource;
import com.miqle.stacks.EcsStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ec2.CfnSpotFleet.InstanceNetworkInterfaceSpecificationProperty;
import software.amazon.awscdk.services.ec2.CfnSpotFleet.SpotFleetLaunchSpecificationProperty;
import software.amazon.awscdk.services.ec2.CfnSpotFleet.SpotFleetRequestConfigDataProperty;
import software.amazon.awscdk.services.iam.CfnInstanceProfile;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;

import static software.amazon.awscdk.services.iam.ManagedPolicy.fromAwsManagedPolicyName;

@Component
public class NodeSpotfleetGroup {

    private CfnSpotFleet cfnSpotFleet;

    @Autowired
    public NodeSpotfleetGroup(EcsStack ecsStack,
                              VpcResource vpcResource,
                              EC2BackedEcsCluster ec2BackedEcsCluster,
                              PrivateSecurityGroup sg,
                              EcsIamRole role) {

        UserData userScript = UserData.forLinux();
        userScript.addCommands(
                ImmutableList.of(
                        "aws configure set region ${AWS::Region}",
                        "echo ECS_CLUSTER=" + ec2BackedEcsCluster.getCluster().getClusterName() + " >> /etc/ecs/ecs.config",
                        "amazon-linux-extras disable docker",
                        "amazon-linux-extras install -y ecs; sudo systemctl enable --now ecs",
                        "curl -s http://localhost:51678/v1/metadata | python -mjson.tool > started.log",
                        "/opt/aws/bin/cfn-signal -e $? --stack ${AWS::StackName} " +
                                "--resource AutoscaleDedicatedGroup --region ${AWS::Region}")
                        .toArray(new String[]{}));

        String imageId = MachineImage.latestAmazonLinux(
                AmazonLinuxImageProps.builder()
                        .generation(AmazonLinuxGeneration.AMAZON_LINUX_2)
                        .build()
        ).getImage(ecsStack.getStack()).getImageId();

        Role spotFleetRole =
                Role.Builder.create(ecsStack.getStack(), "spot-fleet-role")
                        .assumedBy(ServicePrincipal.Builder.create("spotfleet.amazonaws.com")
                                .build())
                        .managedPolicies(
                                Lists.newArrayList(
                                        fromAwsManagedPolicyName("AmazonEC2SpotFleetTaggingRole"))
                        )
                        .build();


        CfnInstanceProfile ip = CfnInstanceProfile.Builder.create(ecsStack.getStack(), "ecs-instance-profile")
                .roles(Lists.newArrayList(role.getRole().getRoleArn()))
                .build();

        CfnSpotFleet.IamInstanceProfileSpecificationProperty ipp =
                CfnSpotFleet.IamInstanceProfileSpecificationProperty.builder()
                        .arn(ip.getAttrArn())
                        .build();

        InstanceNetworkInterfaceSpecificationProperty networkInterfaceSpecificationProperty
                = InstanceNetworkInterfaceSpecificationProperty.builder()
                .deviceIndex(0)
                .subnetId(vpcResource.getVpc().getIsolatedSubnets().get(0).getSubnetId())
                .groups(Lists.newArrayList(sg.getSecurityGroup().getSecurityGroupId()))
                .build();

        SpotFleetLaunchSpecificationProperty launchSpecificationProperty =
                SpotFleetLaunchSpecificationProperty.builder()
                        .iamInstanceProfile(ipp)
                        .imageId(imageId)
                        .instanceType("c5.large")
                        .spotPrice("0.2")
                        .keyName("common")
                        .networkInterfaces(Lists.newArrayList(networkInterfaceSpecificationProperty))
                        .monitoring(CfnSpotFleet.SpotFleetMonitoringProperty.builder()
                                .enabled(true)
                                .build())
                        .userData(userScript.render())
                        .build();

        SpotFleetRequestConfigDataProperty sfConfigData =
                SpotFleetRequestConfigDataProperty.builder()
                        .allocationStrategy("diversified")
                        .iamFleetRole(spotFleetRole.getRoleArn())
                        .launchSpecifications(Lists.newArrayList(launchSpecificationProperty))
                        .replaceUnhealthyInstances(true)
                        .spotPrice("0.2")
                        .targetCapacity(1)
                        .terminateInstancesWithExpiration(true)
                        .build();

        cfnSpotFleet = cfnSpotFleet = CfnSpotFleet.Builder.create(ecsStack.getStack(), "spotfleet-ec2-conf")
                .spotFleetRequestConfigData(sfConfigData)
                .build();
    }
}
