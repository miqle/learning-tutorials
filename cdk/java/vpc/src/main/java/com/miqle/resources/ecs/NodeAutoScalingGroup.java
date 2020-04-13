package com.miqle.resources.ecs;

import com.google.common.collect.ImmutableList;
import com.miqle.resources.vpc.VpcResource;
import com.miqle.stacks.EcsStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.services.autoscaling.AutoScalingGroup;
import software.amazon.awscdk.services.ec2.*;

@Component
public class NodeAutoScalingGroup {

    private AutoScalingGroup autoScalingGroup;

    @Autowired
    public NodeAutoScalingGroup(EcsStack ecsStack,
                                EC2BackedEcsCluster cluster,
                                EcsIamRole role,
                                VpcResource vpcResource) {
        UserData userScript = UserData.forLinux();
        userScript.addCommands(
                ImmutableList.of(
                        "aws configure set region ${AWS::Region}",
                        "echo ECS_CLUSTER=" + cluster.getCluster().getClusterName() + " >> /etc/ecs/ecs.config",
                        "amazon-linux-extras disable docker",
                        "amazon-linux-extras install -y ecs; sudo systemctl enable --now ecs",
                        "curl -s http://localhost:51678/v1/metadata | python -mjson.tool > started.log",
                        "/opt/aws/bin/cfn-signal -e $? --stack ${AWS::StackName} " +
                                "--resource AutoscaleDedicatedGroup --region ${AWS::Region}")
                        .toArray(new String[]{}));
        autoScalingGroup = AutoScalingGroup.Builder
                .create(ecsStack.getStack(), "on-demand-auto-scale")
                .cooldown(Duration.minutes(2))
                .instanceType(InstanceType.of(InstanceClass.COMPUTE5, InstanceSize.LARGE))
                .minCapacity(0)
                .maxCapacity(10)
                .desiredCapacity(0)
                .machineImage(MachineImage.latestAmazonLinux(
                        AmazonLinuxImageProps.builder()
                                .generation(AmazonLinuxGeneration.AMAZON_LINUX_2)
                                .build()
                ))
                .userData(userScript)
                .vpc(vpcResource.getVpc())
                .role(role.getRole())
                .build();
    }
}
