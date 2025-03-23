package com.tomakeitgo.world.data;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShipTableTest implements WithDynamoClient {
    ShipTable shipTable = new ShipTable(dynamodb(), name("ShipTableName"));

    @Test
    void testPut() {
        shipTable.put(new Ship("ShipId", "PlayerId", "standard"));
    }

    @Test
    void testDelete() {
        shipTable.put(new Ship("ShipId", "PlayerId", "standard"));
        shipTable.delete("ShipId");
    }

    @Test
    void testFind_NotPresent() {
        assertTrue(shipTable.find("NotAShipId").isEmpty());
    }

    @Test
    void testFind_Present() {
        shipTable.put(new Ship("ShipId", "PlayerId", "standard"));
        Optional<Ship> shipId = shipTable.find("ShipId");
        assertTrue(shipId.isPresent());
        shipId.ifPresent(i -> {
            assertEquals("ShipId", i.id());
            assertEquals("PlayerId", i.playerId());
        });
    }
}