package com.miqle.stacks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;

@Component
public class VpcStack extends AbstractStack {

    @Autowired
    public VpcStack(@Qualifier("app") Construct app, Environment environment) {
        stack = Stack.Builder.create(app, "vpc-master-stack")
                .env(environment)
                .build();
    }

}
