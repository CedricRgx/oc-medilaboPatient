package com.openclassrooms.patientmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @Column(name = "lastname")
    private String lastname;

    /**
     * The lastname of the patient
     */
    @Column(name = "firstname")
    private String firstname;

    /**
     * The birthdate of the patient
     */
    @Column(name = "birthdate")
    private LocalDate birthdate;

    /**
     * The gender of the patient
     */
    @Column(name = "gender")
    private String gender;

    /**
     * The address of the patient
     */
    @Column(name = "address")
    private String address;

    /**
     * The phone of the patient
     */
    @Column(name = "phone")
    private String phone;
}
