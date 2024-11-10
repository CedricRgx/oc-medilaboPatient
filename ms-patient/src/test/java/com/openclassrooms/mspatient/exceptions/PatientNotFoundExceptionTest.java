package com.openclassrooms.mspatient.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Patient not found with ID: 1";
        PatientNotFoundException exception = new PatientNotFoundException(message);

        assertEquals(message, exception.getMessage(), "Expected exception message to match the provided message");
    }

    @Test
    void testExceptionInheritance() {
        PatientNotFoundException exception = new PatientNotFoundException("Some message");

        assertTrue(exception instanceof RuntimeException, "PatientNotFoundException should inherit from RuntimeException");
    }
}
