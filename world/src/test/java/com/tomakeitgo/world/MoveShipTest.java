package com.tomakeitgo.world;

import com.tomakeitgo.world.data.Coordinate;
import com.tomakeitgo.world.data.Player;
import com.tomakeitgo.world.data.Ship;
import com.tomakeitgo.world.data.ShipLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveShipTest {
    @Test
    void testMove() {
        ShipLocation currentLocation = new ShipLocation(new Coordinate(-1, -1, -1), "shipId");
        var result = new MoveShip().doIt(new MoveShip.MoveRequest(
                new Player("playerId", "name"),
                new Ship("shipId", "playerId", "standard"),
                currentLocation,
                new Coordinate(2,3,5)
        ));

        assertFalse(result.hasErrors());
        assertEquals(currentLocation, result.getResult().currentLocation());
        assertEquals(new ShipLocation(new Coordinate(1,2,4), "shipId"), result.getResult().newLocation());
    }

    @Test
    void given_playerId_mismatch_on_ship_expect_error() {
        ShipLocation currentLocation = new ShipLocation(new Coordinate(-1, -1, -1), "shipId");
        var result = new MoveShip().doIt(new MoveShip.MoveRequest(
                new Player("playerId", "name"),
                new Ship("shipId", "playerIdOne", "standard"),
                currentLocation,
                new Coordinate(2,3,5)
        ));

        assertTrue(result.hasErrors());
    }
}