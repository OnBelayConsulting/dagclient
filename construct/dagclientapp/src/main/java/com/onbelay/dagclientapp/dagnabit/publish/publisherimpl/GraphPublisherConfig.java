package com.onbelay.dagclientapp.dagnabit.publish.publisherimpl;

import com.onbelay.dagclientapp.dagnabit.publish.publisher.GraphNodePublisher;
import com.onbelay.dagclientapp.dagnabit.publish.publisher.GraphRelationshipPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class GraphPublisherConfig {

    @Bean
    @Profile("messaging")
    public GraphNodePublisher graphNodePublisher() {
        return new GraphNodePublisherBean();
    }

    @Bean
    @Profile("!messaging")
    public GraphNodePublisher mockGraphNodePublisher() {
        return new GraphNodePublisherStub();
    }

    @Bean
    @Profile("messaging")
    public GraphRelationshipPublisher graphRelationshipPublisher() {
        return new GraphRelationshipPublisherBean();
    }

    @Bean
    @Profile("!messaging")
    public GraphRelationshipPublisher mockGraphRelationshipPublisher() {
        return new GraphRelationshipPublisherStub();
    }
}
