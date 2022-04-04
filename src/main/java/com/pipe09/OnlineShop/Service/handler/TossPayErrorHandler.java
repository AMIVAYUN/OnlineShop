package com.pipe09.OnlineShop.Service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class TossPayErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series()== HttpStatus.Series.CLIENT_ERROR
                || response.getStatusCode().series()== HttpStatus.Series.SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if(response.getStatusCode().series()== HttpStatus.Series.CLIENT_ERROR){

        }

    }
}
