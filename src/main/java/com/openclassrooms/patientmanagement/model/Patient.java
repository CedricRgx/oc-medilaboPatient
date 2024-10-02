package com.openclassrooms.patientmanagement.model;

import com.openclassrooms.patientmanagement.util.Gender;
import com.openclassrooms.patientmanagement.util.ValidBirthdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    private Long idPat;

    /**
     * The firstname of the patient
     */
    @NotNull
    @NotBlank(message="The lastname is mandatory.")
    @Size(min=2, max=250, message = "The firstname must contain between {min} and {max} characters.")
    @Column(name = "lastname")
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
    @Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="The birthdate format is invalid.")
    @ValidBirthdate(message = "The birthdate must be a valid date format and prior to the current date.")
    //@Constraint(validatedBy = BirthdateValidator.class)
    @Column(name = "birthdate")
    private LocalDate birthdate;

    /**
     * The gender of the patient
     */
    @NotNull
    @NotBlank(message="Gender is mandatory")
    @Column(name="gender")
    private Gender gender;

    /**
     * The address of the patient
     */
    @Size(min=2, max=255, message="The address must contain between {min} and {max} characters.")
    @Column(name="address")
    private String address;

    /**
     * The phone of the patient
     */
    @Pattern(regexp="^\\d{3}-\\d{3}-\\d{4}$", message="The phone number is invalid.")
    @Column(name="phone")
    private String phone;
}
