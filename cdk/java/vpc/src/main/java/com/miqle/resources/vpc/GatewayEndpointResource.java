package com.miqle.resources.vpc;

import com.google.common.collect.Lists;
import com.miqle.stacks.VpcStack;
import software.amazon.awscdk.services.ec2.GatewayVpcEndpoint;
import software.amazon.awscdk.services.ec2.GatewayVpcEndpointAwsService;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;

public abstract class GatewayEndpointResource {

    private GatewayVpcEndpoint gatewayVpcEndpoint;

    public GatewayVpcEndpoint getGatewayVpcEndpoint() {
        return gatewayVpcEndpoint;
    }

    public GatewayEndpointResource(VpcStack stack, VpcResource vpcResource) {
        gatewayVpcEndpoint = GatewayVpcEndpoint.Builder.create(stack.getStack(), getId())
                .subnets(Lists.newArrayList(getPrivateSubnetSelection()))
                .vpc(vpcResource.getVpc())
                .service(getGatewayVpcEndpointAwsService())
                .build();
    }

    private SubnetSelection getPrivateSubnetSelection() {
        return SubnetSelection.builder()
                .subnetType(getSubnetType()).build();
    }

    protected abstract SubnetType getSubnetType();

    protected abstract String getId();

    protected abstract GatewayVpcEndpointAwsService getGatewayVpcEndpointAwsService();
}
