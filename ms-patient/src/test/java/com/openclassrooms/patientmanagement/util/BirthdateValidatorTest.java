package com.openclassrooms.patientmanagement.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class BirthdateValidatorTest {

    private final BirthdateValidator validator = new BirthdateValidator();

    @Test
    public void testIsValid_withNullBirthdate_shouldReturnTrue() {
        LocalDate birthdate = null;
        boolean result = validator.isValid(birthdate, null);
        assertTrue(result);
    }

    @Test
    public void testIsValid_withPastBirthdate_shouldReturnTrue() {
        LocalDate birthdate = LocalDate.of(2000, 1, 1);
        boolean result = validator.isValid(birthdate, null);
        assertTrue(result);
    }

    @Test
    public void testIsValid_withFutureBirthdate_shouldReturnFalse() {
        // Given a birthdate in the future
        LocalDate birthdate = LocalDate.now().plusDays(1);
        boolean result = validator.isValid(birthdate, null);
        assertFalse(result);
    }

    @Test
    public void testIsValid_withCurrentDate_shouldReturnFalse() {
        LocalDate birthdate = LocalDate.now();
        boolean result = validator.isValid(birthdate, null);
        assertFalse(result);
    }
}