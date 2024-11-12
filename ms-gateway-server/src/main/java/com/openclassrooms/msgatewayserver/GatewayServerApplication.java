package com.openclassrooms.msgatewayserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

    /**
     * Configures custom routes for Spring Cloud Gateway to route requests to specific microservices
     * based on path patterns. Each route directs to a corresponding service using load balancing.
     *
     * @param builder the RouteLocatorBuilder used to define the routes
     * @return a RouteLocator containing the configured routes for microservices
     *         "ms-patient", "ms-note", and "ms-diabete"
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ms-patient", r -> r.path("/patient/**")
                    .uri("lb://MS-PATIENT"))
                .route("ms-note", r -> r.path("/note/**")
                    .uri("lb://MS-NOTE"))
                .route("ms-diabete", r -> r.path("/diabete/**")
                    .uri("lb://MS-DIABETE"))
                .build();
    }

    /**
     * Configures dynamic routing for Spring Cloud Gateway using services from a discovery client.
     *
     * @param rdc the reactive discovery client for retrieving registered services
     * @param dlp properties for customizing route discovery
     * @return a route definition locator for dynamic route configuration
     */
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