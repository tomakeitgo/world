package com.tomakeitgo.world.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.tomakeitgo.world.CreateSolarBody;
import com.tomakeitgo.world.data.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CreateSolarBodyHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse>, WithJson {
    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent input, Context context) {
        DynamoDbClient client = DynamoDbClient.create();

        var playerTable = new PlayerTable(client, System.getenv("PlayerTableName"));
        var shipTable = new ShipTable(client, System.getenv("ShipTableName"));
        var shipLocationTable = new ShipLocationTable(client, System.getenv("ShipLocationTable"));

        var request = fromRequest(input, Request.class);
        Optional<Player> player = playerTable.find(request.getPlayerId());
        if (player.isEmpty()) {
            return toResponse(Map.of("type", "error", "message", "Player not found"));
        }
        Optional<Ship> ship = shipTable.find(request.getShipId());
        if (ship.isEmpty()) {
            return toResponse(Map.of("type", "error", "message", "Ship not found"));
        }
        List<ShipLocation> locationOfShip = shipLocationTable.findLocationOfShip(request.getShipId());
        if (locationOfShip.isEmpty()) {
            return toResponse(Map.of("type", "error", "message", "Ship Location not found"));
        }

        var createSolarBody = new CreateSolarBody(new UUIDGenerator());
        var resp = createSolarBody.doIt(new CreateSolarBody.CreateSolarBodyRequest(
                player.get(),
                ship.get(),
                locationOfShip.getFirst(),
                request.getType()
        ));

        if (resp.hasErrors()) {
            return toResponse(Map.of("type", "error", "details", resp.getError()));
        } else {
            return toResponse(Map.of("type", "success"));
        }
    }

    private APIGatewayV2HTTPResponse toResponse(Object resp) {
        APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
        response.setHeaders(Map.of("Content-Type", "application/json"));
        response.setStatusCode(200);
        response.setBody(toJson(resp));
        return response;
    }

    public static class Request {
        String playerId;
        String shipId;
        String type;

        public String getPlayerId() {
            return playerId;
        }

        public Request setPlayerId(String playerId) {
            this.playerId = playerId;
            return this;
        }

        public String getShipId() {
            return shipId;
        }

        public Request setShipId(String shipId) {
            this.shipId = shipId;
            return this;
        }

        public String getType() {
            return type;
        }

        public Request setType(String type) {
            this.type = type;
            return this;
        }
    }
}
