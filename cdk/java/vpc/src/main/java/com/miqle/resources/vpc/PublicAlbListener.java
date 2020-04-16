package com.miqle.resources.vpc;

import com.google.common.collect.Lists;
import com.miqle.stacks.VpcStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.services.elasticloadbalancingv2.*;
import software.constructs.Construct;

@Component
public class PublicAlbListener {

    private ApplicationListener applicationListener;

    @Autowired
    public PublicAlbListener(VpcStack vpcStack, VpcResource vpcResource,
                             PublicAlb publicAlb, @Value("${certification.arn:empty}") String certArn) {
        ApplicationTargetGroup targetGroup =
                ApplicationTargetGroup.Builder.create(vpcStack.getStack(), "go-nowhere")
                        .vpc(vpcResource.getVpc())
                        .build();

        ApplicationListener.Builder.create(vpcStack.getStack(), "secured")
                .certificates(Lists.newArrayList(ListenerCertificate.fromArn(certArn)))
                .port(443)
                .open(true)
                .sslPolicy(SslPolicy.RECOMMENDED)
                .protocol(ApplicationProtocol.HTTPS)
                .loadBalancer(publicAlb.getApplicationLoadBalancer())
                .defaultTargetGroups(Lists.newArrayList(targetGroup))
                .build();



    }

    public ApplicationListener getApplicationListener() {
        return applicationListener;
    }
}
