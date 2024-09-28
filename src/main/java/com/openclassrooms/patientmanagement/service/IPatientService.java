package com.openclassrooms.patientmanagement.service;

import com.openclassrooms.patientmanagement.model.Patient;

import java.util.Optional;

/**
 * Interface for services related to patients within the MediLaboPatient application
 */
public interface IPatientService {

    /**
     * Retrieves all patients
     * @return An iterable containing all patients
     */
    public Iterable<Patient> getPatients();

    /**
     * Retrieves a patient by their ID.
     * @param id The ID of the patient to retrieve.
     * @return An optional containing the patient if found, otherwise empty.
     */
    public Optional<Patient> getPatientById(Long id);

    /**
     * Adds a new patient.
     * @param patient The user to add.
     * @return The added patient.
     */
    public Patient addPatient(Patient patient);

    /**
     * Deletes a patient by their ID.
     * @param id The ID of the patient to delete.
     * @return
     */
    public boolean deletePatientById(Long id);

}