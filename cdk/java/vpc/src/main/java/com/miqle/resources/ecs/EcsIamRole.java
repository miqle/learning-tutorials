package com.miqle.resources.ecs;

import com.miqle.stacks.EcsStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;

import static com.google.common.collect.Lists.newArrayList;
import static software.amazon.awscdk.services.iam.ManagedPolicy.fromAwsManagedPolicyName;

@Component
public class EcsIamRole {
    private Role role;

    @Autowired
    public EcsIamRole(EcsStack ecsStack) {
        role = Role.Builder.create(ecsStack.getStack(), "ecs-role")
                .assumedBy(ServicePrincipal.Builder.create("ec2.amazonaws.com")
                        .build())
                .managedPolicies(
                        newArrayList(
                                fromAwsManagedPolicyName("AmazonEC2ContainerServiceAutoscaleRole"),
                                fromAwsManagedPolicyName("AmazonEC2ContainerServiceforEC2Role"),
                                fromAwsManagedPolicyName("AmazonEC2ContainerServiceRole")
                        )
                )
                .build();
    }

    public Role getRole() {
        return role;
    }
}
