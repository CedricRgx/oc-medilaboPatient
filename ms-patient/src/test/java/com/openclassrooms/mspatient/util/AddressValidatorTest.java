package com.openclassrooms.mspatient.util;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class AddressValidatorTest {

    private AddressValidator addressValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        addressValidator = new AddressValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    void isValid_ShouldReturnTrue_WhenAddressIsNull() {
        assertTrue(addressValidator.isValid(null, context), "Expected null address to be valid");
    }

    @Test
    void isValid_ShouldReturnTrue_WhenAddressIsEmpty() {
        assertTrue(addressValidator.isValid("", context), "Expected empty address to be valid");
    }

    @Test
    void isValid_ShouldReturnTrue_WhenAddressIsWithinValidLength() {
        assertTrue(addressValidator.isValid("123 Main St", context), "Expected address within valid length to be valid");
    }

    @Test
    void isValid_ShouldReturnFalse_WhenAddressIsTooShort() {
        assertFalse(addressValidator.isValid("A", context), "Expected address shorter than 2 characters to be invalid");
    }

    @Test
    void isValid_ShouldReturnFalse_WhenAddressIsTooLong() {
        String longAddress = "A".repeat(256);  // Create a string with 256 characters
        assertFalse(addressValidator.isValid(longAddress, context), "Expected address longer than 255 characters to be invalid");
    }
}
