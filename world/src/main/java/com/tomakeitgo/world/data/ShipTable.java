package com.tomakeitgo.world.data;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;
import java.util.Optional;

public class ShipTable {
    private final String name;
    private final DynamoDbClient dynamoDbClient;

    public ShipTable(DynamoDbClient client, String name) {
        this.name = name;
        this.dynamoDbClient = client;
    }

    public void put(Ship ship) {
        dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName(name)
                .item(Map.of(
                        "ShipId", AttributeValue.fromS(ship.id()),
                        "PlayerId", AttributeValue.fromS(ship.playerId())
                ))
                .build()
        );
    }

    public void delete(String shipId) {
        dynamoDbClient.deleteItem(DeleteItemRequest.builder()
                .tableName(name)
                .key(Map.of(
                        "ShipId", AttributeValue.fromS(shipId)
                ))
                .build()
        );
    }

    public Optional<Ship> find(String shipId) {
        var response = dynamoDbClient.getItem(GetItemRequest.builder()
                .tableName(name)
                .key(Map.of(
                        "ShipId", AttributeValue.fromS(shipId))
                )
                .build()
        );

        if (!response.hasItem()) return Optional.empty();
        var item = response.item();
        return Optional.of(new Ship(
                item.get("ShipId").s(),
                item.getOrDefault("PlayerId", AttributeValue.fromS("")).s()
        ));
    }
}
