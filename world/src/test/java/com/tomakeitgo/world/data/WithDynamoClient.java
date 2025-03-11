package com.tomakeitgo.world.data;

import org.junit.jupiter.api.BeforeAll;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudformation.CloudFormationClient;
import software.amazon.awssdk.services.cloudformation.model.Output;
import software.amazon.awssdk.services.cloudformation.model.Stack;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.HashMap;

public interface WithDynamoClient {
    DynamoDbClient client = DynamoDbClient.builder().region(Region.US_WEST_2).build();
    HashMap<String, String> names = new HashMap<>();

    default DynamoDbClient dynamodb(){
        return client;
    }

    default String name(String name){
        return names.get(name);
    }


    @BeforeAll
    static void findTableDefinitions() {
        try (var client = CloudFormationClient
                .builder()
                .region(Region.US_WEST_2)
                .build()) {


            for (Stack stack : client.describeStacks().stacks()) {
                if (stack.stackName().equals("world-data-test")) {
                    for (Output output : stack.outputs()) {
                        names.put(output.outputKey(), output.outputValue());
                    }
                }
            }
        }
    }
}
