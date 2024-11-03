package com.openclassrooms.mspatient.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validates that a phone number is null, empty, or matches a specific pattern: "012-345-6789".
 */
public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    /**
     * Regex pattern for validating a phone number
     */
    private static final String REGEX = "^\\d{3}-\\d{3}-\\d{4}$";

    /**
     * Validates the phone number format. Accepts null or empty values.
     *
     * @param phone the phone number to validate
     * @param context validation context
     * @return True if the phone number is null, empty, or matches the pattern; False otherwise
     */
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if(phone==null || phone.isEmpty()) {
            return true;
        }
        return phone.matches(REGEX);
    }

}
