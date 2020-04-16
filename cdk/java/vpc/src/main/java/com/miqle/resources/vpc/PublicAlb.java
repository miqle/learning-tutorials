package com.miqle.resources.vpc;

import com.google.common.collect.Lists;
import com.miqle.stacks.VpcStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.elasticloadbalancingv2.*;

@Component
public class PublicAlb {

    private ApplicationLoadBalancer applicationLoadBalancer;

    @Autowired
    public PublicAlb(VpcStack vpcStack,
                     VpcResource vpcResource,
                     PublicSecurityGroup publicSecurityGroup
                     ) {
        applicationLoadBalancer =
        ApplicationLoadBalancer.Builder.create(vpcStack.getStack(), "public-alb")
                .deletionProtection(true)
                .internetFacing(true)
                .ipAddressType(IpAddressType.DUAL_STACK)
                .loadBalancerName("public-alb")
                .vpc(vpcResource.getVpc())
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PUBLIC)
                        .build())
                .securityGroup(publicSecurityGroup.getSecurityGroup())
                .build();

    }

    public ApplicationLoadBalancer getApplicationLoadBalancer() {
        return applicationLoadBalancer;
    }
}
