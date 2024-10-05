package com.openclassrooms.patientmanagement.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * Validates that a given LocalDate is a valid birthdate,
 * meaning it is prior to the current date.
 */
public class BirthdateValidator implements ConstraintValidator<ValidBirthdate, LocalDate> {


    /**
     * Checks if the provided LocalDate is valid and before the current date.
     *
     * @param birthdate The birthdate to validate.
     * @param context Context in which the constraint is evaluated.
     * @return True if the birthdate is valid or null or false otherwise.
     */
    @Override
    public boolean isValid(LocalDate birthdate, ConstraintValidatorContext context) {
        if (birthdate == null) {
            return true;
        }
        return birthdate.isBefore(LocalDate.now());
    }

}