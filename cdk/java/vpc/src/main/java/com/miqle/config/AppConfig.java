package com.miqle.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;

@Configuration
@PropertySource({"classpath:vpc-master/stack.properties"  })
@ComponentScan(basePackages = {"com.miqle.stacks", "com.miqle.resources"})
public class AppConfig {

    @Value("${subnet.private.name.prefix}")
    private String value;

    private static Logger LOGGER = LoggerFactory
            .getLogger(AppConfig.class);

    @Bean
    public App app() {
        return new App();
    }

    @Bean
    public Environment environment() {
        return Environment.builder().region("us-east1").build();
    }

}
