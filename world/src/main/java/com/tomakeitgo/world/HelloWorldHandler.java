package com.tomakeitgo.world;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;

import java.util.Map;

public class HelloWorldHandler implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent input, Context context) {
        APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
        response.setStatusCode(200);
        response.setIsBase64Encoded(false);
        response.setHeaders(Map.of(
                "Content-Type", "text/plain"
        ));
        response.setBody("Hello World! Different Data!");

        return response;
    }
}
