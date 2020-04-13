package com.miqle.resources.vpc;

import com.miqle.stacks.VpcStack;
import com.miqle.utils.ExportNameConfigEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.core.CfnOutput;
import software.amazon.awscdk.core.CfnOutputProps;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.Vpc;

import java.util.List;
import java.util.stream.Collectors;

import static com.miqle.utils.ExportNameConfigEnum.*;

@Component
public class VpcResource {

    private final Vpc vpc;

    @Autowired
    public VpcResource(VpcStack vpcStack,
                       List<SubnetConfiguration> subnetConfigurationList) {
        vpc = Vpc.Builder.create(vpcStack.getStack(), "vpc-master")
                .cidr("162.0.0.0/8")
                .subnetConfiguration(subnetConfigurationList)
                .maxAzs(2)
                .natGateways(0)
                .enableDnsHostnames(true)
                .enableDnsSupport(true)
                .build();
    }

    public Vpc getVpc() {
        return vpc;
    }
}
