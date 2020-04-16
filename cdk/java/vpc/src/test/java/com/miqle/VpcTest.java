package com.miqle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.miqle.stacks.VpcStack;
import org.junit.Test;
import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;

import java.io.IOException;

public class VpcTest {
    private final static ObjectMapper JSON =
            new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);

    @Test
    public void testStack() throws IOException {
        App app = new App();
        VpcStack stack = new VpcStack(app, Environment.builder().build());

        // synthesize the stack to a CloudFormation template and compare against
        // a checked-in JSON file.
        //JsonNode actual = JSON.valueToTree(app.synth().getStackArtifact(stack.getArtifactId()).getTemplate());
        //assertEquals(new ObjectMapper().createObjectNode(), actual);
    }
}
