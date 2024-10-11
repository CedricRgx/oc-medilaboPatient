package com.openclassrooms.msclientui;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The main class for the Eureka server.
 * This class initializes the Spring Boot application and contains the entry point method.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication implements CommandLineRunner {

    /**
     * The entry point for the Eureka server. It initializes and starts the Spring Boot application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

    /**
     * The run method implementation from CommandLineRunner interface.
     * This method is executed after the Spring application context is initialized.
     * It currently prints "Eureka Server UP !!" to the console.
     *
     * @param args The command-line arguments passed to the application.
     */
    @Override
    public void run(String... args) {
        System.out.println("Eureka Server UP !!");
    }
}