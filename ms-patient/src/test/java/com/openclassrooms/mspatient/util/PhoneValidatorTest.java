package com.openclassrooms.mspatient.util;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class PhoneValidatorTest {

    private PhoneValidator phoneValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        phoneValidator = new PhoneValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    public void isValid_ShouldReturnTrue_WhenPhoneIsNull() {
        assertTrue(phoneValidator.isValid(null, context), "Expected null phone number to be valid");
    }

    @Test
    public void isValid_ShouldReturnTrue_WhenPhoneIsEmpty() {
        assertTrue(phoneValidator.isValid("", context), "Expected empty phone number to be valid");
    }

    @Test
    public void isValid_ShouldReturnTrue_WhenPhoneMatchesPattern() {
        assertTrue(phoneValidator.isValid("012-345-6789", context), "Expected valid phone number format to be valid");
    }

    @Test
    public void isValid_ShouldReturnFalse_WhenPhoneDoesNotMatchPattern() {
        assertFalse(phoneValidator.isValid("0123456789", context), "Expected phone number without dashes to be invalid");
        assertFalse(phoneValidator.isValid("012-3456-789", context), "Expected phone number with incorrect format to be invalid");
        assertFalse(phoneValidator.isValid("123-45-67890", context), "Expected phone number with incorrect format to be invalid");
        assertFalse(phoneValidator.isValid("ABC-DEF-GHIJ", context), "Expected phone number with letters to be invalid");
    }
}
