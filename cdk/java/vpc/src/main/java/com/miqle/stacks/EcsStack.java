package com.miqle.stacks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.Stack;

@Component
public class EcsStack extends AbstractStack{

    @Autowired
    public EcsStack(@Qualifier("app") Construct app, Environment environment) {
        stack = Stack.Builder.create(app, "ecs-stack")
                .env(environment)
                .build();
    }
}
