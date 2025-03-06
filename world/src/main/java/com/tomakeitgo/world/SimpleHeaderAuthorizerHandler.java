package com.tomakeitgo.world;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2CustomAuthorizerEvent;

import java.util.Map;

public class SimpleHeaderAuthorizerHandler implements RequestHandler<APIGatewayV2CustomAuthorizerEvent, Object> {
    @Override
    public Object handleRequest(APIGatewayV2CustomAuthorizerEvent input, Context context) {
        return Map.of("isAuthorized", input.getHeaders().containsKey("magic-token"));
    }
}
