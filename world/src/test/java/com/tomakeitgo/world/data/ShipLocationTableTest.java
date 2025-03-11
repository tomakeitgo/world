package com.tomakeitgo.world.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShipLocationTableTest implements WithDynamoClient {
    ShipLocationTable shipLocationTable = new ShipLocationTable(dynamodb(), name("ShipLocationTableName"));

    @Test
    void testPut() {
        shipLocationTable.put(new ShipLocation(new Coordinate(0, 0, 0), "goop"));

        assertTrue(shipLocationTable.find(new ShipLocation(new Coordinate(0, 0, 0), "goop")).isPresent());
    }

    @Test
    void testFind() {
        shipLocationTable.put(new ShipLocation(new Coordinate(-1, 1, -1), "goop-1"));
        shipLocationTable.put(new ShipLocation(new Coordinate(-1, 1, -1), "goop-2"));
        shipLocationTable.put(new ShipLocation(new Coordinate(-1, 1, -1), "goop-3"));

        var items = shipLocationTable.findShipsAt(new Coordinate(-1,1,-1));
        assertEquals(3, items.size());
    }

    @Test
    void testMove(){
        ShipLocation start = new ShipLocation(new Coordinate(0, 0, 0), "move-test");
        ShipLocation end = new ShipLocation(new Coordinate(1, 1, 1), "move-test");

        shipLocationTable.put(start);
        shipLocationTable.move(start, end);

        var shipLocations = shipLocationTable.findLocationOfShip("move-test");
        assertEquals(1, shipLocations.size());
        shipLocationTable.remove(end);
    }
}