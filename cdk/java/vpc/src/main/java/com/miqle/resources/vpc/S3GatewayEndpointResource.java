package com.miqle.resources.vpc;

import com.miqle.stacks.VpcStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.services.ec2.GatewayVpcEndpointAwsService;
import software.amazon.awscdk.services.ec2.SubnetType;

@Component
public class S3GatewayEndpointResource extends GatewayEndpointResource {

    @Autowired
    public S3GatewayEndpointResource(VpcStack stack, VpcResource vpcResource) {
        super(stack, vpcResource);
    }

    @Override
    protected SubnetType getSubnetType() {
        return SubnetType.ISOLATED;
    }

    @Override
    protected String getId() {
        return "S3Endpoint";
    }

    @Override
    protected GatewayVpcEndpointAwsService getGatewayVpcEndpointAwsService() {
        return GatewayVpcEndpointAwsService.S3;
    }
}
