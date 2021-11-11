package org.iota.transactiontracking.scheduler.config;

import org.iota.transactiontracking.scheduler.exception.ApiRequestException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Configuration
public class TaskRestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return (clientHttpResponse.getStatusCode().series() == CLIENT_ERROR
                || clientHttpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        switch (clientHttpResponse.getStatusCode()) {

            case FORBIDDEN:
                throw new ApiRequestException("Access Denied.","Authentication Required",HttpStatus.FORBIDDEN);
            case GATEWAY_TIMEOUT:
                throw new ApiRequestException("External service down.", "Service not available", HttpStatus.SERVICE_UNAVAILABLE);
            case BAD_GATEWAY:
                throw new ApiRequestException("FCM service error.", "Server Error", HttpStatus.BAD_GATEWAY);
            case UNAUTHORIZED:
                throw new ApiRequestException("Wrong API  Credentials.", "Bad Credentials", HttpStatus.UNAUTHORIZED);
            default:
                throw new ApiRequestException("An unexpected error occurred.", "Unknown Error", clientHttpResponse.getStatusCode());

        }
    }
}
