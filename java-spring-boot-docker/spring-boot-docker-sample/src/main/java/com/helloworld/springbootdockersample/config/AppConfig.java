package com.helloworld.springbootdockersample.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.helloworld.springbootdockersample.controllers")
public class AppConfig {
}
