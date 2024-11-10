package com.openclassrooms.msnote;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.Transactional;

/**
 * The main class for the MediLaboNote application.
 * This class initializes the Spring Boot application and contains the entry point method.
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.openclassrooms.msnote.proxy")
@EnableDiscoveryClient
public class MSNoteApplication implements CommandLineRunner {

    /**
     * The entry point for the MediLaboNote application. It initializes and starts the Spring Boot application.
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(MSNoteApplication.class, args);
    }

    /**
     * The run method implementation from CommandLineRunner interface.
     * This method is executed after the Spring application context is initialized.
     * It currently prints "MS-Note UP !!" to the console.
     * @param args The command-line arguments passed to the application.
     */
    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("MS-Note UP !!");
    }

}
