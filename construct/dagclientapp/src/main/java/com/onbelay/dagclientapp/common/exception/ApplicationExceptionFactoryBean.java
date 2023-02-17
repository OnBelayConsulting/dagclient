package com.onbelay.dagclientapp.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onbelay.core.entity.snapshot.ErrorHoldingSnapshot;
import com.onbelay.dagclient.enums.TransactionErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class ApplicationExceptionFactoryBean implements ApplicationExceptionFactory {

    @Autowired
    private ObjectMapper objectMapper;

    public ApplicationWebClientException newApplicationWebClientExceptionTimeout(String target) {
        ErrorHoldingSnapshot errorHoldingSnapshot = new ErrorHoldingSnapshot(TransactionErrorCode.WEB_CALL_TO_DAGNABIT_FAILED.getCode());
        errorHoldingSnapshot.setErrorMessage(TransactionErrorCode.WEB_CALL_TO_DAGNABIT_FAILED.getDescription());
        try {
            String json = objectMapper.writeValueAsString(errorHoldingSnapshot);
            return new ApplicationWebClientException(HttpStatus.GATEWAY_TIMEOUT, json);
        } catch (JsonProcessingException e) {
            return new ApplicationWebClientException(HttpStatus.BAD_REQUEST, "{}");
        }
    }

}
