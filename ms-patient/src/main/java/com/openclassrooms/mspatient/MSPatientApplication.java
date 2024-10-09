package com.openclassrooms.mspatient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

/**
 * The main class for the MediLaboPatient application.
 * This class initializes the Spring Boot application and contains the entry point method.
 */
@SpringBootApplication
public class MSPatientApplication implements CommandLineRunner {

	/**
	 * The entry point for the MediLaboPatient application. It initializes and starts the Spring Boot application.
	 * @param args The command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MSPatientApplication.class, args);
	}

	/**
	 * The run method implementation from CommandLineRunner interface.
	 * This method is executed after the Spring application context is initialized.
	 * It currently prints "MS-Patient UP !!" to the console.
	 * @param args The command-line arguments passed to the application.
	 */
	@Override
	@Transactional
	public void run(String... args) {
		System.out.println("MS-Patient UP !!");
	}

}
