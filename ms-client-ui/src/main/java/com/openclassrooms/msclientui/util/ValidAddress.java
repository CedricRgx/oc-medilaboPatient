package com.openclassrooms.msclientui.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for validating address length (size between 2 and 255 characters or null or empty).
 */
@Constraint(validatedBy = AddressValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAddress {

    /**
     * The default error message to be returned if the phone number is invalid.
     */
    String message() default "The address is invalid. The address must contain between 2 and 255 characters or be empty.";

    /**
     * Allows specification of validation groups, to which this constraint belongs.
     */
    Class<?>[] groups() default {};

    /**
     * Can be used by clients of the Bean Validation API to assign custom payload objects to a constraint.
     */
    Class<? extends Payload>[] payload() default {};
}
