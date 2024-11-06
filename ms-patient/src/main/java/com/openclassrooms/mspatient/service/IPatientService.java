package com.openclassrooms.mspatient.service;

import com.openclassrooms.mspatient.model.Patient;

import java.util.Optional;

/**
 * Interface for services related to patients within the MediLaboPatient application
 */
public interface IPatientService {

    /**
     * Retrieves all patients
     * @return An iterable containing all patients
     */
    Iterable<Patient> getPatients();

    /**
     * Retrieves a patient by their ID.
     * @param id The ID of the patient to retrieve.
     * @return An optional containing the patient if found, otherwise empty.
     */
    Optional<Patient> getPatientById(Long id);

    /**
     * Saves a new patient.
     * @param patient The user to add.
     * @return The added patient.
     */
    Patient savePatient(Patient patient);

    /**
     * Updates a patient to the repository.
     * @param patient The Patient object to be updated.
     * @return The updated Patient object.
     */
    Patient updatePatient(Patient patient);

    /**
     * Deletes a patient by their ID.
     * @param id The ID of the patient to be deleted.
     * @return True if the deletion was successful, otherwise False.
     */
    boolean deletePatientById(Long id);

}
