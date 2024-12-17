package com.openclassrooms.mspatient.model;

import com.openclassrooms.mspatient.util.Gender;
import com.openclassrooms.mspatient.util.ValidAddress;
import com.openclassrooms.mspatient.util.ValidBirthdate;
import com.openclassrooms.mspatient.util.ValidPhone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

/**
 * Represents a patient in the MediLaboPatient application.
 * This class is annotated as a JPA entity
 */
@Getter
@Setter
@Entity
@Table(name = "patient")
public class Patient {

    /**
     * The unique identifier for the patient
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long id;

    /**
     * The firstname of the patient
     */
    @NotNull
    @NotBlank(message="The lastname is mandatory.")
    @Size(min=2, max=250, message = "The lastname must contain between {min} and {max} characters.")
    @Column(name="lastname")
    private String lastname;

    /**
     * The lastname of the patient
     */
    @NotNull
    @NotBlank(message="The firstname is mandatory.")
    @Size(min=2, max=250, message="The firstname must contain between {min} and {max} characters.")
    @Column(name="firstname")
    private String firstname;

    /**
     * The birthdate of the patient
     */
    @NotNull
    @ValidBirthdate(message = "The birthdate must be a valid date format and prior to the current date.")
    @Column(name="birthdate")
    private LocalDate birthdate;

    /**
     * The gender of the patient
     */
    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private Gender gender;

    /**
     * The address of the patient
     */
    @ValidAddress(message="The address is invalid.")
    @Column(name="address")
    private String address;

    /**
     * The phone of the patient
     * This field is optional, but if provided, it must match the pattern 012-345-6789.
     */
    @ValidPhone(message="The phone number is invalid.")
    @Column(name="phone")
    private String phone;

    /**
     * The status of the patient
     */
    @Column(name="is_active")
    private boolean isActive;
}
