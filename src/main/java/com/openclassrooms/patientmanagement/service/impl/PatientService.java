package com.openclassrooms.patientmanagement.service.impl;

import com.openclassrooms.patientmanagement.model.Patient;
import com.openclassrooms.patientmanagement.repository.PatientRepository;
import com.openclassrooms.patientmanagement.service.IPatientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The PatientService class provides business logic related to Patient entities.
 */
@Slf4j
@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Retrieves all users from the repository.
     * @return An Iterable containing all users.
     */
    public List<Patient> getPatients(){
        log.info("Retrieve all patients");
        return patientRepository.findAll();
    }

    /**
     * Retrieves an user from the repository.
     * @return An Optional containing the patient.
     */
    public Optional<Patient> getPatientById(Long id) {
        log.info("Retrieve a patient");
        return patientRepository.findById(id);
    }

    /**
     * Adds a new patient to the repository.
     * @param patient The Patient object to be added.
     * @return The added Patient object.
     */
    @Transactional
    public Patient addPatient(Patient patient) {
        log.info("Adding an patient");
        return patientRepository.save(patient);
    }

    /**
     * Updates a patient to the repository.
     * @param patient The Patient object to be updated.
     * @return The updated Patient object.
     */
    @Transactional
    public Patient updatePatient(Patient patient) {
        log.info("Adding an patient");
        if(!patientRepository.existsById(patient.getIdPat())){
            throw new EntityNotFoundException("Patient not found");
        }
        log.info("Updating a patient");
        return patientRepository.save(patient);
    }

    /**
     * Deletes a patient by their ID.
     * @param id The ID of the patient to be deleted.
     */
    @Transactional
    public boolean deletePatientById(Long id) {
        try {
            patientRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Unexpected error occurred while deleting patient with id: {}", id, e);
            return false;
        }
    }
}
