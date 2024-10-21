package com.openclassrooms.msclientui;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The main class for the UI of MediLaboPatient application.
 * This class initializes the Spring Boot application and contains the entry point method.
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.openclassrooms.msclientui.proxy")
@EnableDiscoveryClient
public class MSClientUIApplication implements CommandLineRunner {

    /**
     * The entry point for the MediLaboPatient application. It initializes and starts the Spring Boot application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(MSClientUIApplication.class, args);
    }

    /**
     * The run method implementation from CommandLineRunner interface.
     * This method is executed after the Spring application context is initialized.
     * It currently prints "MS-Client-UI UP !!" to the console.
     *
     * @param args The command-line arguments passed to the application.
     */
    @Override
    public void run(String... args) {
        System.out.println("MS-Client-UI UP !!");
    }

}
