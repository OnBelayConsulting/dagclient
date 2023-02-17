package com.onbelay.dagclientapp.common.exception;

import org.springframework.stereotype.Component;

@Component
public interface ApplicationExceptionFactory {

    public ApplicationWebClientException newApplicationWebClientExceptionTimeout(String target);

}
