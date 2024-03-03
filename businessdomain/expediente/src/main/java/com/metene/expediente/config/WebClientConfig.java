package com.metene.expediente.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientConfig.class);
    private final DiscoveryClient discoveryClient;

    @Value(value = "${cuidadano.service.name}")
    private String ciudadanoServiceName;
    @Value(value = "${cuidadanos.api.base-path}")
    private String cuidadanoAPIBasePath;
    @Value(value = "${apis.timeout}")
    private Long timeoutApi;

    public WebClientConfig(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Bean
    public WebClient webClientApis() {
        return createWebClient();
    }

    private WebClient createWebClient() {

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMillis(timeoutApi));

        return WebClient.builder()
                .baseUrl(getServiceBaseURL().toString())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    private StringBuilder getServiceBaseURL() {
        StringBuilder serviceURI = new StringBuilder();
        try {
            ServiceInstance instance = discoveryClient.getInstances(ciudadanoServiceName).get(0);
            serviceURI.append(instance.getUri().toString());
        } catch (Exception e) {
            LOGGER.error("WebClientConfig::getServiceBaseURL:: error {} ", e.getMessage());
        }

        return serviceURI.append(cuidadanoAPIBasePath);
    }
}
