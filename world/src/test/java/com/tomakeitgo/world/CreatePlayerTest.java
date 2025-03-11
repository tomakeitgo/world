package com.tomakeitgo.world;

import com.tomakeitgo.world.data.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreatePlayerTest {

    @Test
    void test() {
        var createPlayer = new CreatePlayer(() -> "Id");

        var response = createPlayer.doIt("Will");
        assertEquals("Id", response.player().id());
        assertEquals("Will", response.player().name());

        assertEquals("Id", response.ship().id());
        assertEquals("Id", response.ship().playerId());

        assertEquals(new Coordinate(0,0,0), response.shipLocation().coordinate());
        assertEquals("Id", response.shipLocation().shipId());
    }
}