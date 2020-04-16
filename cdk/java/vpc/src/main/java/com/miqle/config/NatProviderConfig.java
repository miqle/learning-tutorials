package com.miqle.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awscdk.services.ec2.*;

@Configuration
public class NatProviderConfig {

    @Bean
    public NatProvider natProvider(@Value("${keypair.name:ec2-access}") String keyPairName) {
        return NatProvider.instance(NatInstanceProps.builder()
                .instanceType(InstanceType.of(InstanceClass.STANDARD3, InstanceSize.MICRO))
                .keyName(keyPairName)
                .machineImage(MachineImage.latestAmazonLinux(AmazonLinuxImageProps.builder()
                        .generation(AmazonLinuxGeneration.AMAZON_LINUX_2).build()))
                .build()
        );
    }
}
