package com.metene.expediente.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {
    @Value(value = "${url.apis.basepath}")
    private String baseUrlApis;
    @Value(value = "${apis.timeout}")
    private Long timeoutApi;

    @Bean
    public WebClient webClientApis() {
        return createWebClient(baseUrlApis, timeoutApi);
    }

    private WebClient createWebClient(String baseUrl, Long timeout) {

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMillis(timeout));

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies(
                        ExchangeStrategies.builder()
                                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(500 * 1024))
                                .build())
                .build();

    }
}
