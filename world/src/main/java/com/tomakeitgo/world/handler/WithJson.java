package com.tomakeitgo.world.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.google.gson.Gson;

import java.util.Base64;

public interface  WithJson {

    default <T> T fromRequest(APIGatewayV2HTTPEvent input, Class<T> type) {
        if (input.getIsBase64Encoded()){
            return new Gson().fromJson(new String(Base64.getDecoder().decode(input.getBody())), type);
        }else {
            return new Gson().fromJson(input.getBody(), type);
        }
    }

    default String toJson(Object response){
        return new Gson().toJson(response);
    }
}
