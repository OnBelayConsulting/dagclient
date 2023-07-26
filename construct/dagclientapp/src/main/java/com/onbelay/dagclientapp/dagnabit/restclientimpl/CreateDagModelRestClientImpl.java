package com.onbelay.dagclientapp.dagnabit.restclientimpl;

import com.onbelay.dagclientapp.common.exception.ApplicationExceptionFactory;
import com.onbelay.dagclientapp.common.exception.ApplicationWebClientException;
import com.onbelay.dagclientapp.common.snapshot.WebResult;
import com.onbelay.dagclientapp.dagnabit.restclient.CreateDagModelRestClient;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelSnapshot;
import com.onbelay.dagclientapp.dagnabit.snapshot.ModelResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;

public class CreateDagModelRestClientImpl extends AbstractDagModelRestClient implements CreateDagModelRestClient {
    private static final Logger logger = LogManager.getLogger();

    private static final String URL = "/api/models";

    private String host;

    private String port;
    private String contextPath;

    @Autowired
    private ApplicationExceptionFactory applicationExceptionFactory;

    @Autowired
    private WebClient webClient;

    public CreateDagModelRestClientImpl
            (String host,
             String port,
             String contextPath) {

        super();
        this.host = host;
        this.port = port;
        this.contextPath = contextPath;
    }

    @Override
    public ModelResult saveDagModel(DagModelSnapshot snapshot) {

        String riskAppUrl = String.format(
                URL,
                host,
                port,
                contextPath);

        String errorDetected;
        try {
             return this.webClient.post()
                     .uri(uriBuilder -> uriBuilder
                             .scheme("http")
                             .host(host)
                             .port(port)
                             .path(contextPath)
                             .path(URL)
                    .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(snapshot)
                    .retrieve()
                    .bodyToMono(ModelResult.class)
                     .retryWhen(Retry.backoff(3, Duration.ofSeconds(10))
                                        .filter(checkThrowable)
                                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                                                       applicationExceptionFactory.newApplicationWebClientExceptionTimeout("Risk")))
                    .block();

        } catch (WebClientException r) {
            logger.error("Webclient Rest call failed: {}", r.getMessage());
            throw new ApplicationWebClientException(HttpStatus.BAD_GATEWAY, r.getMessage());
        }
    }

}
