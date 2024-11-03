package com.openclassrooms.msclientui.model;

import com.openclassrooms.msclientui.util.Gender;

import com.openclassrooms.msclientui.util.ValidAddress;
import com.openclassrooms.msclientui.util.ValidBirthdate;
import com.openclassrooms.msclientui.util.ValidPhone;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Represents a patient for the client ui
 */

@Getter
@Setter
public class Patient {

    /**
     * The id of the patient
     */
    private Long id;

    /**
     * The firstname of the patient
     */
    @NotNull
    @NotBlank(message="The lastname is mandatory.")
    @Size(min=2, max=250, message = "The lastname must contain between {min} and {max} characters.")
    private String lastname;

    /**
     * The lastname of the patient
     */
    @NotNull
    @NotBlank(message="The firstname is mandatory.")
    @Size(min=2, max=250, message="The firstname must contain between {min} and {max} characters.")
    private String firstname;

    /**
     * The birthdate of the patient
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull
    @ValidBirthdate(message="The birthdate must be prior to the current date.")
    private LocalDate birthdate;

    /**
     * The gender of the patient
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * The address of the patient
     */
    @ValidAddress(message="The address is invalid.")
    private String address;

    /**
     * The phone of the patient
     * This field is optional, but if provided, it must match the pattern 012-345-6789.
     */
    @ValidPhone(message="The phone number is invalid.")
    private String phone;
}
