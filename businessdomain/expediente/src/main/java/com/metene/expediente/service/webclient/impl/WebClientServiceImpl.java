package com.metene.expediente.service.webclient.impl;

import com.metene.expediente.service.webclient.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebClientServiceImpl implements WebClientService  {
    private final WebClient webClient;

    @Autowired
    public WebClientServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public <T> Mono<T> get(String basePath, Class<T> responseType, String...pathSegments) {
        return this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(basePath)
                        .pathSegment(pathSegments)
                        .build())
                .retrieve()
                .bodyToMono(responseType);
    }

    @Override
    public <T> Mono<T> post(String basePath, MultiValueMap<String, String> queryParams, Class<T> responseType, Object paramsBean) {
        return this.webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(basePath)
                        .queryParams(queryParams).build())
                .body(BodyInserters.fromValue(paramsBean))
                .retrieve()
                .bodyToMono(responseType);
    }
}
