package com.openclassrooms.msdiabete.model;

import com.openclassrooms.msdiabete.util.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents a patient in the MediLaboPatient application.
 */
@Getter
@Setter
public class Patient {

    /**
     * The unique identifier for the patient
     */
    private Long id;

    /**
     * The firstname of the patient
     */
    private String lastname;

    /**
     * The lastname of the patient
     */
    private String firstname;

    /**
     * The birthdate of the patient
     */
    private LocalDate birthdate;

    /**
     * The gender of the patient
     */
    private Gender gender;

}
