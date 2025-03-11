package com.tomakeitgo.world.data;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;
import java.util.Optional;

public class PlayerTable {
    private final String name;
    private final DynamoDbClient dynamoDbClient;

    public PlayerTable(DynamoDbClient client, String name) {
        this.name = name;
        this.dynamoDbClient = client;
    }

    public void put(Player player) {
        dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName(name)
                .item(Map.of(
                        "PlayerId", AttributeValue.fromS(player.id()),
                        "PlayerName", AttributeValue.fromS(player.name())
                ))
                .build()
        );
    }

    public void remove(String playerId) {
        dynamoDbClient.deleteItem(DeleteItemRequest.builder()
                .tableName(name)
                .key(Map.of("PlayerId", AttributeValue.fromS(playerId)))
                .build());
    }

    public Optional<Player> find(String playerId) {
        var response = dynamoDbClient.getItem(GetItemRequest.builder()
                .tableName(name)
                .key(Map.of(
                        "PlayerId", AttributeValue.fromS(playerId))
                )
                .build()
        );

        if (!response.hasItem()) return Optional.empty();
        var item = response.item();
        return Optional.of(new Player(
                item.get("PlayerId").s(),
                item.getOrDefault("PlayerName", AttributeValue.fromS("")).s()
        ));
    }

}
