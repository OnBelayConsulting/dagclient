package com.onbelay.dagclientapp.dagnabit.restclientimpl;

import com.onbelay.dagclientapp.common.exception.ApplicationExceptionFactory;
import com.onbelay.dagclientapp.common.exception.ApplicationWebClientException;
import com.onbelay.dagclientapp.dagnabit.restclient.GetDagModelsRestClient;
import com.onbelay.dagclientapp.dagnabit.snapshot.DagModelCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.util.retry.Retry;

import java.time.Duration;

public class GetDagModelsRestClientImpl extends AbstractDagModelRestClient implements GetDagModelsRestClient {

    private static final Logger logger = LogManager.getLogger();

    private String host;

    private String port;
    private String contextPath;

    @Autowired
    private ApplicationExceptionFactory applicationExceptionFactory;

    @Autowired
    private WebClient webClient;

    private static final String URL = "/api/models";

    public GetDagModelsRestClientImpl(
            String host,
            String port,
            String baAppPath) {
        this.host = host;
        this.port = port;
        this.contextPath = contextPath;
    }

    @Override
    public DagModelCollection getDagModels(
            String query,
            long start,
            int limit) {

        DagModelCollection snapshot;

        try {
            snapshot = this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host(host)
                            .port(port)
                            .path(contextPath)
                            .path(URL)
                            .queryParam("query", query)
                            .queryParam("start", start)
                            .queryParam("limit", limit)
                            .build())
                    .retrieve()
                    .bodyToMono(DagModelCollection.class)
                    .retryWhen(Retry.backoff(3, Duration.ofSeconds(20))
                                       .filter(checkThrowable)
                                       .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                                                      applicationExceptionFactory.newApplicationWebClientExceptionTimeout("RiskApp")))
                    .block();
        } catch (WebClientException r) {
            logger.error("Webclient Rest call failed: {}", r.getMessage());
            throw new ApplicationWebClientException(HttpStatus.BAD_GATEWAY, "failed");
        }

        return snapshot;
    }
    
}
