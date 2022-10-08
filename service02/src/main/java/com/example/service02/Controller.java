package com.example.service02;

import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerWebClientBuilderBeanPostProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

@RestController
public class Controller {

    @Inject
    private WebClient.Builder clientBuilder;

    @GetMapping("/")
    public Mono<Payload> getRoot() {
        return clientBuilder.build().get()
                .uri("http://service-01/")
                .retrieve()
                .bodyToMono(Payload.class)
                .map(p -> new Payload("service-one says "+p.text()));
    }
}
