package com.example.gateway;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

/**
 * @author Ryan Baxter
 */
// tag::code[]
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"app.target-service.url=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
public class GatewayApplicationTest {

  @Autowired
  private WebTestClient webClient;

  @Test
  public void contextLoads() throws Exception {
    //Stubs
    stubFor(get(urlEqualTo("/"))
        .willReturn(aResponse()
          .withBody("{\"text\": \"hello\"}")
          .withHeader("Content-Type", "application/json")));

    webClient
      .get().uri("/serviceTwo")
      .exchange()
      .expectStatus().isOk()
      .expectBody()
      .jsonPath("$.text").isEqualTo("hello");

    stubFor(get(urlEqualTo("/foo"))
            .willReturn(aResponse()
              .withBody("{\"text\": \"hello\"}")
              .withHeader("Content-Type", "application/json")));

        webClient
          .get().uri("/serviceTwo/foo")
          .exchange()
          .expectStatus().isOk()
          .expectBody()
          .jsonPath("$.text").isEqualTo("hello");

 }
}