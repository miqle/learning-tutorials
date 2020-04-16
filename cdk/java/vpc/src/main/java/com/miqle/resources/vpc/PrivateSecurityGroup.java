package com.miqle.resources.vpc;

import com.miqle.stacks.VpcStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.core.CfnOutput;
import software.amazon.awscdk.core.CfnOutputProps;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SecurityGroupProps;

@Component
public class PrivateSecurityGroup {
    private SecurityGroup securityGroup;

    @Autowired
    public PrivateSecurityGroup(VpcStack vpcStack,
                                VpcResource vpcResource,
                                PublicSecurityGroup publicSecurityGroup) {
        SecurityGroupProps facingInternetProps = SecurityGroupProps.builder()
                .vpc(vpcResource.getVpc())
                .securityGroupName("InternalSG")
                .allowAllOutbound(true)
                .build();
        securityGroup = new SecurityGroup(vpcStack.getStack(), "InternalSG", facingInternetProps);
        securityGroup.addIngressRule(securityGroup, Port.allTcp());
        securityGroup.addIngressRule(publicSecurityGroup.getSecurityGroup(), Port.allTcp());

    }

    public SecurityGroup getSecurityGroup() {
        return securityGroup;
    }
}
