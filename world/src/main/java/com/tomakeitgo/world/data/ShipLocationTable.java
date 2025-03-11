package com.tomakeitgo.world.data;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShipLocationTable {
    private final String name;
    private final DynamoDbClient dynamoDbClient;

    public ShipLocationTable(DynamoDbClient client, String name) {
        this.name = name;
        this.dynamoDbClient = client;
    }

    public void put(ShipLocation ship) {
        dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName(name)
                .item(Map.of(
                        "LocationId", AttributeValue.fromS(ship.coordinate().toStorageFormat()),
                        "ShipId", AttributeValue.fromS(ship.shipId())
                ))
                .build()
        );
    }

    public void remove(ShipLocation ship) {
        dynamoDbClient.deleteItem(DeleteItemRequest.builder()
                .tableName(name)
                .key(Map.of(
                        "LocationId", AttributeValue.fromS(ship.coordinate().toStorageFormat()),
                        "ShipId", AttributeValue.fromS(ship.shipId())
                ))
                .build()
        );
    }

    public Optional<ShipLocation> find(ShipLocation shipLocation) {
        var response = dynamoDbClient.getItem(GetItemRequest.builder()
                .tableName(name)
                .key(Map.of(
                        "LocationId", AttributeValue.fromS(shipLocation.coordinate().toStorageFormat()),
                        "ShipId", AttributeValue.fromS(shipLocation.shipId())
                ))
                .build()
        );

        if (!response.hasItem()) return Optional.empty();
        var item = response.item();
        return Optional.of(new ShipLocation(
                Coordinate.fromStorageFormat(item.get("LocationId").s()),
                item.get("ShipId").s()
        ));
    }

    public void move(ShipLocation source, ShipLocation destination) {
        dynamoDbClient.transactWriteItems(TransactWriteItemsRequest.builder()
                .transactItems(
                        TransactWriteItem.builder().put(
                                Put.builder()
                                        .tableName(name)
                                        .item(Map.of(
                                                "ShipId", AttributeValue.fromS(destination.shipId()),
                                                "LocationId", AttributeValue.fromS(destination.coordinate().toStorageFormat()))
                                        )
                                        .build()
                        ).build(),
                        TransactWriteItem.builder().delete(
                                Delete.builder()
                                        .tableName(name)
                                        .key(Map.of(
                                                "ShipId", AttributeValue.fromS(source.shipId()),
                                                "LocationId", AttributeValue.fromS(source.coordinate().toStorageFormat()))
                                        )
                                        .build()
                        ).build()
                ).build()
        );
    }

    public List<ShipLocation> findLocationOfShip(String shipId) {
        var result = dynamoDbClient.query(QueryRequest
                .builder()
                .tableName(name)
                .indexName("FindShip")
                .select(Select.ALL_ATTRIBUTES)
                .keyConditionExpression("ShipId = :ShipId")
                .expressionAttributeValues(Map.of(
                        ":ShipId", AttributeValue.fromS(shipId))
                )
                .build()
        );
        if (!result.hasItems()) return Collections.emptyList();
        return result.items().stream().map(i -> {
            return new ShipLocation(
                    Coordinate.fromStorageFormat(i.get("LocationId").s()),
                    i.get("ShipId").s()
            );
        }).toList();
    }

    public List<ShipLocation> findShipsAt(Coordinate coordinate) {
        return dynamoDbClient
                .queryPaginator(QueryRequest
                        .builder()
                        .tableName(name)
                        .select(Select.ALL_ATTRIBUTES)
                        .keyConditionExpression("LocationId = :LocationId")
                        .expressionAttributeValues(Map.of(
                                ":LocationId", AttributeValue.fromS(coordinate.toStorageFormat()))
                        )
                        .build()
                )
                .items()
                .stream()
                .map(i -> {
                    return new ShipLocation(
                            Coordinate.fromStorageFormat(i.get("LocationId").s()),
                            i.get("ShipId").s()
                    );
                })
                .toList();
    }
}
