package com.openclassrooms.mspatient.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for the ValidAddress annotation. Ensures the address is
 * null, empty, or between 2 and 255 characters.
 */
public class AddressValidator implements ConstraintValidator<ValidAddress, String> {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 255;

    /**
     * Checks if the provided address is valid.
     *
     * @param address the address to validate
     * @param context context in which the constraint is evaluated
     * @return True if the address is null, empty, or within the valid length range;
     *         False otherwise
     */
    @Override
    public boolean isValid(String address, ConstraintValidatorContext context) {
        if(address == null || address.isEmpty()) {
            return true;
        }
        return address.length() >= MIN_LENGTH && address.length() <= MAX_LENGTH;
    }
}
