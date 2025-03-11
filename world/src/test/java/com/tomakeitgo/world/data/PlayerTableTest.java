package com.tomakeitgo.world.data;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTableTest implements WithDynamoClient {

    PlayerTable playerTable = new PlayerTable(dynamodb(), name("PlayerTableName"));

    @Test
    void testPut() {
        playerTable.put(new Player("My Cool Player", "My Cool Player"));
    }

    @Test
    void testDelete() {
        playerTable.put(new Player("My Cool Player", "My Cool Player"));
        playerTable.remove("My Cool Player");
        assertTrue(playerTable.find("My Cool Player").isEmpty());
    }

    @Test
    void testFind_DoesNotExist() {
        assertTrue(playerTable.find("NotReal").isEmpty());
    }

    @Test
    void testFind_DoesExist() {
        playerTable.put(new Player("My Cool Player", "My Cool Player"));
        Optional<Player> player = playerTable.find("My Cool Player");
        assertFalse(player.isEmpty());
        player.ifPresent((i) -> {
            assertEquals(i.id(), "My Cool Player");
            assertEquals(i.name(), "My Cool Player");
        });
    }
}