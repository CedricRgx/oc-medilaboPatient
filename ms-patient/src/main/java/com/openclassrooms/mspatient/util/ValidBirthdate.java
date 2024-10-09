package com.openclassrooms.mspatient.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for validating that a LocalDate field is a valid birthdate, meaning it is prior to the current date.
 */
@Constraint(validatedBy = BirthdateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBirthdate {

    /**
     * The default error message to be returned if the birthdate is invalid.
     */
    String message() default "The birthdate is invalid.";

    /**
     * Allows specification of validation groups, to which this constraint belongs.
     */
    Class<?>[] groups() default {};

    /**
     * Can be used by clients of the Bean Validation API to assign custom payload objects to a constraint.
     */
    Class<? extends Payload>[] payload() default {};
}