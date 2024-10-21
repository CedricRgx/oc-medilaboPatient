package com.openclassrooms.msgatewayserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * The main class for the Gateway server.
 * This class initializes the Spring Boot application and contains the entry point method.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication implements CommandLineRunner {

    /**
     * The entry point for the Gateway server. It initializes and starts the Spring Boot application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ms-patient", r -> r.path("/patient/**")
                        .uri("lb://MS-PATIENT"))
                .build();
    }

    @Bean
    public DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp) {
        return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
    }

    /**
     * The run method implementation from CommandLineRunner interface.
     * This method is executed after the Spring application context is initialized.
     * It currently prints "Gateway Server UP !!" to the console.
     *
     * @param args The command-line arguments passed to the application.
     */
    @Override
    public void run(String... args) {
        System.out.println("Gateway Server UP !!");
    }
}