package com.openclassrooms.msclientui.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for validating phone numbers (pattern "012-345-6789" or null or empty).
 */
@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhone {

    /**
     * The default error message to be returned if the phone number is invalid.
     */
    String message() default "The phone number is invalid. Expected format: 012-345-6789";

    /**
     * Allows specification of validation groups, to which this constraint belongs.
     */
    Class<?>[] groups() default {};

    /**
     * Can be used by clients of the Bean Validation API to assign custom payload objects to a constraint.
     */
    Class<? extends Payload>[] payload() default {};
}
