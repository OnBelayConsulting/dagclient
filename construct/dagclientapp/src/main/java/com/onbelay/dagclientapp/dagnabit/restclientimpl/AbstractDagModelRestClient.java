package com.onbelay.dagclientapp.dagnabit.restclientimpl;

import com.onbelay.dagclientapp.common.exception.ApplicationWebClientException;

import java.util.function.Predicate;

public abstract class AbstractDagModelRestClient {


    protected Predicate<Throwable> checkThrowable = (ex) -> {
        if (ex instanceof ApplicationWebClientException) {
            ApplicationWebClientException applicationWebClientException = (ApplicationWebClientException) ex;
            return applicationWebClientException.getHttpStatus().is5xxServerError();
        } else {
            return false;
        }
    };



}
