package com.tomakeitgo.world.starforge;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;
import java.util.Optional;

public class StarForgeNPCTable {

    private final DynamoDbClient client;
    private final String table;

    public StarForgeNPCTable(DynamoDbClient client, String table) {
        this.client = client;
        this.table = table;
    }

    public void put(StarForgeNPCRecord record) {
        client.putItem((r) -> {
            r.tableName(table);
            r.item(Map.of(
                    "Id", AttributeValue.fromS(record.id()),
                    "PlayerId", AttributeValue.fromS(record.playerId()),
                    "ShipId", AttributeValue.fromS(record.shipId()),
                    "Ring", AttributeValue.fromN(record.ring() + ""),
                    "Offset", AttributeValue.fromN(record.offset() + ""),
                    "ActionId", AttributeValue.fromN(record.actionId() + "")
            ));
        });
    }

    public Optional<StarForgeNPCRecord> find(String id) {
        var resp = client.getItem((r) -> {
            r
                    .tableName(table)
                    .key(Map.of("Id", AttributeValue.fromS(id)));
        });

        if (!resp.hasItem()) return Optional.empty();
        var item = resp.item();
        return Optional.of(new StarForgeNPCRecord(
                item.get("Id").s(),
                item.get("PlayerId").s(),
                item.get("ShipId").s(),
                Integer.parseInt(item.get("Ring").n()),
                Integer.parseInt(item.get("Offset").n()),
                Integer.parseInt(item.get("ActionId").n())
        ));
    }
}
