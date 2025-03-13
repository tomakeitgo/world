package com.tomakeitgo.world.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.google.gson.Gson;
import com.tomakeitgo.world.MoveShip;
import com.tomakeitgo.world.data.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.Base64;
import java.util.Map;

public class MoveShipHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse>, WithJson {
    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent input, Context context) {
        try {
            DynamoDbClient client = DynamoDbClient.create();
            var playerTable = new PlayerTable(client, System.getenv("PlayerTableName"));
            var shipTable = new ShipTable(client, System.getenv("ShipTableName"));
            var shipLocationTable = new ShipLocationTable(client, System.getenv("ShipLocationTable"));

            var request = fromRequest(input, Request.class);

            var player = playerTable.find(request.getPlayerId());
            var ship = shipTable.find(request.getShipId());
            var shipLocation = shipLocationTable.findLocationOfShip(request.getShipId());

            var errors = new MoveShip.Errors();
            if (player.isEmpty())
                errors.add("playerId", "required");
            if (ship.isEmpty())
                errors.add("shipId", "required");
            if (shipLocation.isEmpty())
                errors.add("shipId", "ship without location");

            if (errors.hasErrors()) {
                APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
                response.setHeaders(Map.of("Content-Type", "application/json"));
                response.setStatusCode(200);
                response.setBody(toJson(Map.of("type", "error", "errors", errors)));
                return response;
            }

            var result = new MoveShip().doIt(new MoveShip.MoveRequest(
                    player.get(),
                    ship.get(),
                    shipLocation.get(0),
                    new Coordinate(request.getX(), request.getY(), request.getZ()))
            );

            if (result.hasErrors()) {
                APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
                response.setHeaders(Map.of("Content-Type", "application/json"));
                response.setStatusCode(200);
                response.setBody(toJson(Map.of("type", "error", "errors", errors)));
                return response;
            }

            shipLocationTable.move(
                    result.getResult().currentLocation(),
                    result.getResult().newLocation()
            );

            APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
            response.setHeaders(Map.of("Content-Type", "application/json"));
            response.setStatusCode(200);
            response.setBody(toJson(Map.of("type", "success")));
            return response;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static class Request {
        String playerId;
        String shipId;
        int x;
        int y;
        int z;

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

        public int getX() {
            return x;
        }

        public Request setX(int x) {
            this.x = x;
            return this;
        }

        public int getY() {
            return y;
        }

        public Request setY(int y) {
            this.y = y;
            return this;
        }

        public int getZ() {
            return z;
        }

        public Request setZ(int z) {
            this.z = z;
            return this;
        }
    }
}
