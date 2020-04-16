package com.miqle.resources.vpc;

import com.google.common.collect.Lists;
import com.miqle.stacks.VpcStack;
import software.amazon.awscdk.services.ec2.GatewayVpcEndpoint;
import software.amazon.awscdk.services.ec2.GatewayVpcEndpointAwsService;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;

public abstract class GatewayEndpointResource {

    protected GatewayVpcEndpoint gatewayVpcEndpoint;

    public GatewayEndpointResource(VpcStack stack, VpcResource vpcResource) {
        gatewayVpcEndpoint =
                GatewayVpcEndpoint.Builder.create(stack.getStack(), getId())
                        .service(getGatewayVpcEndpointAwsService())
                        .vpc(vpcResource.getVpc())
                        .subnets(Lists.newArrayList(SubnetSelection.builder()
                                .subnetType(getSubnetType())
                                .build()))
                        .build();
    }

    public GatewayVpcEndpoint getGatewayVpcEndpoint() {
        return gatewayVpcEndpoint;
    }

    protected abstract GatewayVpcEndpointAwsService getGatewayVpcEndpointAwsService();
    protected abstract String getId();
    protected abstract SubnetType getSubnetType();
}
