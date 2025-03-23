package com.tomakeitgo.world;

import com.tomakeitgo.world.data.IdGenerator;
import com.tomakeitgo.world.data.Player;
import com.tomakeitgo.world.data.Ship;
import com.tomakeitgo.world.data.ShipLocation;

import java.util.Set;

public class CreateSolarBody {
    private final IdGenerator idGenerator;

    public CreateSolarBody(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public MoveShip.ErrorOrValue<CreateSolarBodyResponse> doIt(
            CreateSolarBodyRequest request
    ) {
        if (!request.creator().playerId().equals(request.player.id()))
            return new MoveShip.ErrorOrValue<>(
                    new MoveShip.Errors().add("playerId", "player doesn't own ship")
            );
        if (!request.creator.type().equals("star-forge"))
            return new MoveShip.ErrorOrValue<>(
                    new MoveShip.Errors().add("type", "ship type must be star-forge")
            );
        if (!Set.of("star", "planet", "dwarf").contains(request.type()))
            return new MoveShip.ErrorOrValue<>(
                    new MoveShip.Errors().add("type", "type must be star, planet, dwarf")
            );


        var body = new Ship(
                idGenerator.generateId(),
                request.player.id(),
                request.type()
        );
        return new MoveShip.ErrorOrValue<>(new CreateSolarBodyResponse(
                body,
                new ShipLocation(
                        request.location.coordinate(),
                        body.id()
                )
        ));
    }

    public record CreateSolarBodyRequest(
            Player player,
            Ship creator,
            ShipLocation location,
            String type
    ) {
    }

    public record CreateSolarBodyResponse(
            Ship body,
            ShipLocation bodyLocation
    ) {
    }
}
