package com.openclassrooms.mspatient.service.impl;

import com.openclassrooms.mspatient.exceptions.PatientNotFoundException;
import com.openclassrooms.mspatient.model.Patient;
import com.openclassrooms.mspatient.repository.PatientRepository;
import com.openclassrooms.mspatient.service.IPatientService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

//    @Autowired
//    private FeignClient feignClient;

    /**
     * Retrieves all users from the repository.
     * @return An Iterable containing all users.
     */
    public List<Patient> getPatients(){
        log.info("Retrieve all patients");
        List<Patient> patients = patientRepository.findAll();
        List<Patient> patientsActive = new ArrayList<>();
        for(Patient patient : patients){
            if(patient.isActive()){
                patientsActive.add(patient);
            }
        }
        return patientsActive;
    }

    /**
     * Retrieves a user from the repository.
     * @return An Optional containing the patient.
     */
    public Optional<Patient> getPatientById(Long id) {
        log.info("Retrieve a patient with ID: {}", id);
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.get().isActive()){
            return patient;
        }else{
            return patient.empty();
        }
    }

    /**
     * Adds a new patient to the repository.
     * @param patient The Patient object to be added.
     * @return The added Patient object.
     */
    @Transactional
    public Patient savePatient(Patient patient) {
        log.info("Adding an patient");
        patient.setActive(true);
        return patientRepository.save(patient);
    }

    /**
     * Updates a patient to the repository.
     * @param patient The Patient object to be updated.
     * @return The updated Patient object.
     */
    @Transactional
    public Patient updatePatient(Patient patient) {
        log.info("Updating a patient with ID: {}", patient.getId());
        if (!patientRepository.existsById(patient.getId())) {
            log.error("Patient not found with ID: {}", patient.getId());
            throw new PatientNotFoundException("Patient not found with ID: " + patient.getId());
        }
        return patientRepository.save(patient);
    }

    /**
     * Deactivates a patient by their ID.
     *
     * @param id The ID of the patient to be deactivated.
     * @return True if the deactivation was successful, False otherwise.
     */
    @Transactional
    public boolean deactivatePatientById(Long id) {
        log.info("Deactivating a patient with ID: {}", id);
        try {
            Patient patient = patientRepository.findById(id).get();
            patient.setActive(false);
            patientRepository.save(patient);
            return true;
        } catch (Exception e) {
            log.error("Unexpected error occurred while deactivating patient with ID: {}", id, e);
            return false;
        }
    }

    /**
     * Deletes a patient by their ID.
     * @param id The ID of the patient to be deleted.
     * @return True if the deletion was successful, otherwise False.
     */
//    @Transactional
//    public boolean deletePatientById(Long id) {
//        log.info("Deleting a patient with ID: {}", id);
//        try {
//            if (!patientRepository.existsById(id)) {
//                log.error("Patient not found with ID: {}", id);
//                throw new PatientNotFoundException("Patient not found with ID: " + id);
//            }
//            try {
//                boolean notesExist = feignClient.existsNotesByPatientId(id);
//                if (notesExist) {
//                    feignClient.deleteNotesByPatientId(id);
//                    log.info("Successfully deleted notes for patient ID: {}", id);
//                } else {
//                    log.info("No notes found for patient ID: {}, skipping note deletion.", id);
//                }
//            } catch (Exception e) {
//                log.error("Failed to check/delete notes for patient ID: {}. Root cause: {}", id, e.getMessage(), e);
//                return false;
//            }
//            patientRepository.deleteById(id);
//            return true;
//        } catch (Exception e) {
//            log.error("Unexpected error occurred while deleting patient with ID: {}", id, e);
//            return false;
//        }
//    }

}
