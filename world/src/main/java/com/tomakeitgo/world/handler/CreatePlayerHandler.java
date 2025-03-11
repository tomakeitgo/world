package com.tomakeitgo.world.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.google.gson.Gson;
import com.tomakeitgo.world.CreatePlayer;
import com.tomakeitgo.world.data.PlayerTable;
import com.tomakeitgo.world.data.ShipLocationTable;
import com.tomakeitgo.world.data.ShipTable;
import com.tomakeitgo.world.data.UUIDGenerator;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.Base64;
import java.util.Map;

public class CreatePlayerHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent input, Context context) {
        Gson gson = new Gson();

        String body = input.getIsBase64Encoded() ? new String(Base64.getDecoder().decode(input.getBody())) : input.getBody();
        var request = gson.fromJson(body, Request.class);

        CreatePlayer createPlayer = new CreatePlayer(new UUIDGenerator());
        CreatePlayer.Result result = createPlayer.doIt(request.getPlayerName());

        DynamoDbClient client = DynamoDbClient.create();

        new PlayerTable(client, System.getenv("PlayerTableName")).put(result.player());
        new ShipTable(client, System.getenv("ShipTableName")).put(result.ship());
        new ShipLocationTable(client, System.getenv("ShipLocationTable")).put(result.shipLocation());


        APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
        response.setStatusCode(200);
        response.setHeaders(Map.of("Content-Type", "application/json"));
        response.setBody(gson.toJson(new ResponseBody(result)));
        return response;
    }

    public static class Request {
        private String playerName;

        public String getPlayerName() {
            return playerName;
        }

        public Request setPlayerName(String playerName) {
            this.playerName = playerName;
            return this;
        }
    }

    public static class ResponseBody {
        String playerId;
        String shipId;

        public ResponseBody(CreatePlayer.Result result) {
            this.playerId = result.player().id();
            this.shipId = result.ship().id();
        }
    }
}
