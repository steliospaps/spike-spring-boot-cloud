package com.example.gateway;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.gateway.GatewayApplication.TargetServiceConfigurationProperties;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableConfigurationProperties(TargetServiceConfigurationProperties.class)
@LoadBalancerClient(name="service-one")
public class GatewayApplication {

    private static final Logger log = LoggerFactory.getLogger(GatewayApplication.class);
    
    @ConfigurationProperties(prefix = "app.target-service")
  public record TargetServiceConfigurationProperties(String url) {}

  public static void main(String[] args) {
      SpringApplication.run(GatewayApplication.class, args);
    }

  @Bean
  public RouteLocator myRoutes(RouteLocatorBuilder builder, TargetServiceConfigurationProperties props) {
    String httpUri = props.url();
    log.info("initialised with uri {}",httpUri);
    return builder.routes()
      .route(p -> p
        .path("/serviceTwo/*")
        .filters(uriSpec -> uriSpec.stripPrefix(1))
        .uri(httpUri))
      .route(p -> p
              .path("/serviceTwo")
              .filters(uriSpec -> uriSpec.stripPrefix(1))
              .uri(httpUri))
      .build();
  }
  
  @Bean
  @LoadBalanced()
  public WebClient.Builder webClientBuilder() {
      return WebClient.builder();
  }
}
