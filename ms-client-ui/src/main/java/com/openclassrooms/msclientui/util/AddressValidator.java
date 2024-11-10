package com.openclassrooms.msclientui.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Validator for the ValidAddress annotation. Ensures the address is
 * null, empty, or between 2 and 255 characters.
 */
@Slf4j
public class AddressValidator implements ConstraintValidator<ValidAddress, String> {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 255;

    /**
     * Checks if the provided address is valid.
     *
     * @param address the address to validate
     * @param context context in which the constraint is evaluated
     * @return True if the address is null, empty, or within the valid length range;
     * False otherwise
     */
    @Override
    public boolean isValid(String address, ConstraintValidatorContext context) {
        if (address == null || address.isEmpty()) {
            log.info("Address is null or empty (valid)");
            return true;
        }
        boolean isValid = address.length() >= MIN_LENGTH && address.length() <= MAX_LENGTH;
        if (!isValid) {
            log.warn("The address {} is not valid", address);
        }
        return isValid;
    }
}
