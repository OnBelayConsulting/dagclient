package com.onbelay.dagclientapp.dagnabit.restclientimpl;

import com.onbelay.dagclientapp.dagnabit.restclient.CreateDagModelRestClient;
import com.onbelay.dagclientapp.dagnabit.restclient.GetDagModelsRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DagnabitRestConfig {

    @Value(value ="${dagnabit.host:localhost}")
    private String dagnabitHost;

    @Value(value ="${dagnabit.port:9000}")
    private String dagnabitPort;

    @Value(value ="${dagnabit.contextname:Dagnabit}")
    private String dagnabitContextName;

    @Value(value ="${dagnabit.useAPI:false}")
    private boolean useAPI;

    @Bean
    public GetDagModelsRestClient getDagModelsRestClient() {
        if (useAPI)
            return new GetDagModelsRestClientImpl(
                    dagnabitHost,
                    dagnabitPort,
                    dagnabitContextName);
        else
            return new GetDagModelsRestClientStub();
    }

    @Bean
    public CreateDagModelRestClient createDagModelRestClient() {
        if (useAPI)
            return new CreateDagModelRestClientImpl(
                    dagnabitHost,
                    dagnabitPort,
                    dagnabitContextName);
        else
            return new CreateDagModelRestClientStub();
    }

}
