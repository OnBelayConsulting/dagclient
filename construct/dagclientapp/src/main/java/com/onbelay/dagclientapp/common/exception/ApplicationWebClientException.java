package com.onbelay.dagclientapp.common.exception;

import org.springframework.http.HttpStatus;

public class ApplicationWebClientException extends RuntimeException{

    HttpStatus httpStatus;
    String jsonBody;

    public ApplicationWebClientException(HttpStatus httpStatus, String jsonBody) {
        this.httpStatus = httpStatus;
        this.jsonBody = jsonBody;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getJsonBody() {
        return jsonBody;
    }
}
