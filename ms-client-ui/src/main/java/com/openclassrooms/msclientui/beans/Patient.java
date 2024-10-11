package com.openclassrooms.msclientui.beans;

import com.openclassrooms.msclientui.util.Gender;

import lombok.Getter;
import lombok.Setter;

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

    /**
     * The address of the patient
     */
    private String address;

    /**
     * The phone of the patient
     */
    private String phone;
}
