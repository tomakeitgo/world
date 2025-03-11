package com.tomakeitgo.world;

import com.tomakeitgo.world.data.*;

public class CreatePlayer {
    private final IdGenerator idGenerator;

    public CreatePlayer(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Result doIt(String playerName) {
        String playerId = idGenerator.generateId();
        String shipId = idGenerator.generateId();

        return new Result(
                new Player(playerId, playerName),
                new Ship(shipId, playerId),
                new ShipLocation(new Coordinate(0, 0, 0), shipId)
        );
    }

    public record Result(
            Player player,
            Ship ship,
            ShipLocation shipLocation
    ) {
    }
}
