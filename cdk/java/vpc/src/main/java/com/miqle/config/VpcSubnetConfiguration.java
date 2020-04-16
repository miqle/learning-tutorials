package com.miqle.config;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetType;

import java.util.List;

@Configuration
public class VpcSubnetConfiguration {

    @Bean
    public List<SubnetConfiguration> subnetConfigurationList(@Value("${cidr.mask:16}") int cidrMask) {
        // Create three public subnet
        SubnetConfiguration publicSubnet = newPublicSubnet("DMZ", cidrMask);

        SubnetConfiguration privateSubnet = newPrivateSubnet("Private", cidrMask);
        return Lists.newArrayList(publicSubnet, privateSubnet);
    }

    @NotNull
    private SubnetConfiguration newPrivateSubnet(String name, int cidrMask) {
        return software.amazon.awscdk.services.ec2.SubnetConfiguration.builder()
                .cidrMask(cidrMask)
                .subnetType(SubnetType.ISOLATED)
                .name(name)
                .build();
    }

    @NotNull
    private SubnetConfiguration newPublicSubnet(String name, int cidrMask) {
        return software.amazon.awscdk.services.ec2.SubnetConfiguration.builder()
                .cidrMask(cidrMask)
                .subnetType(SubnetType.PUBLIC)
                .name(name)
                .build();
    }

}
