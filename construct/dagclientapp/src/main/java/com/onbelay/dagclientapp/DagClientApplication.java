package com.onbelay.dagclientapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@ComponentScan(basePackages = {"com.onbelay.core.*", "com.onbelay.dagclientapp.*", "com.onbelay.dagclient.*"})
@EntityScan(basePackages = {"com.onbelay.*"})
@SpringBootApplication
public class DagClientApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(DagClientApplication.class)
                .run(args);
    }

}
