package com.openclassrooms.mspatient.exceptions;

/**
 * Custom exception thrown when a requested patient is not found in the application's data storage.
 */
public class PatientNotFoundException extends RuntimeException {

    /**
     * Constructs a new PatientNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public PatientNotFoundException(String message) {
        super(message);
    }

}