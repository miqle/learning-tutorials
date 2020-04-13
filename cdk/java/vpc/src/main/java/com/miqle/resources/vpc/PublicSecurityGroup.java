package com.miqle.resources.vpc;

import com.miqle.stacks.VpcStack;
import com.miqle.utils.ExportNameConfigEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.core.CfnOutput;
import software.amazon.awscdk.core.CfnOutputProps;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SecurityGroupProps;

import static com.miqle.utils.ExportNameConfigEnum.*;

@Component
public class PublicSecurityGroup {
    private SecurityGroup securityGroup;

    @Autowired
    public PublicSecurityGroup(VpcStack vpcStack, VpcResource vpcResource) {
        SecurityGroupProps facingInternetProps = SecurityGroupProps.builder()
                .vpc(vpcResource.getVpc())
                .securityGroupName("PublicSG")
                .allowAllOutbound(true)
                .build();
        securityGroup = new SecurityGroup(vpcStack.getStack(), "PublicSG", facingInternetProps);
        securityGroup.getConnections()
                .allowFromAnyIpv4(Port.tcp(80), "Http request");
        securityGroup.getConnections()
                .allowFromAnyIpv4(Port.tcp(443), "Https request");

        new CfnOutput(vpcStack.getStack(), "PublicSGOut",
                CfnOutputProps.builder().exportName(PUBLIC_SG_ID.name())
                        .value(securityGroup.getSecurityGroupId()).build());
    }

    public SecurityGroup getSecurityGroup() {
        return securityGroup;
    }
}
