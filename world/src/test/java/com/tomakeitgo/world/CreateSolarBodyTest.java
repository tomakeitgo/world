package com.tomakeitgo.world;

import com.tomakeitgo.world.data.Coordinate;
import com.tomakeitgo.world.data.Player;
import com.tomakeitgo.world.data.Ship;
import com.tomakeitgo.world.data.ShipLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateSolarBodyTest {

    private CreateSolarBody creator;

    @BeforeEach
    void setUp() {
        creator = new CreateSolarBody(() -> "newId");
    }

    @Test
    void test() {
        var result = creator.doIt(new CreateSolarBody.CreateSolarBodyRequest(
                new Player("playerId", ""),
                new Ship("shipId", "playerId", "star-forge"),
                new ShipLocation(new Coordinate(0, 0, 0), ""),
                "star"
        ));

        assertFalse(result.hasErrors());

        assertEquals("newId", result.getResult().body().id());
        assertEquals("playerId", result.getResult().body().playerId());
        assertEquals("star", result.getResult().body().type());

        assertEquals("newId", result.getResult().bodyLocation().shipId());
        assertEquals(new Coordinate(0, 0, 0), result.getResult().bodyLocation().coordinate());
    }

    @Test
    void testInvalidType(){
        var result = creator.doIt(new CreateSolarBody.CreateSolarBodyRequest(
                new Player("playerId", ""),
                new Ship("shipId", "playerId", "star-forge"),
                new ShipLocation(new Coordinate(0, 0, 0), ""),
                "standard"
        ));

        assertTrue(result.hasErrors());
        assertEquals(1, result.getError().getErrors().size());
    }
}