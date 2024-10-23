package com.openclassrooms.msclientui.model;

import com.openclassrooms.msclientui.util.Gender;

import com.openclassrooms.msclientui.util.ValidBirthdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @ValidBirthdate(message="The birthdate must be a valid date format and prior to the current date.")
    private LocalDate birthdate;

    /**
     * The gender of the patient
     */
    //@Pattern(regexp="^[MF]$", message="The gender has to be M or F.")
    private Gender gender;

    /**
     * The address of the patient
     */
    @Size(min=2, max=255, message="The address must contain between {min} and {max} characters.")
    private String address;

    /**
     * The phone of the patient
     */
    @Pattern(regexp="^\\d{3}-\\d{3}-\\d{4}$", message="The phone number is invalid.")
    private String phone;
}
