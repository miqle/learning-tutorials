package com.miqle.resources.ecs;

import com.miqle.resources.vpc.VpcResource;
import com.miqle.stacks.EcsStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.services.ecs.Cluster;

@Component
public class EC2BackedEcsCluster {

    private Cluster cluster;

    @Autowired
    public EC2BackedEcsCluster(EcsStack ecsStack, VpcResource vpcResource) {

        cluster = Cluster.Builder.create(ecsStack.getStack(), "ecs-cluster")
                .clusterName("ec2-backed-ecs-cluster")
                .vpc(vpcResource.getVpc())
                .build();
    }

    public Cluster getCluster() {
        return cluster;
    }
}
