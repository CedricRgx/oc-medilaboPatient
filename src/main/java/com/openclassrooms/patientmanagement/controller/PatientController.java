package com.openclassrooms.patientmanagement.controller;

import com.openclassrooms.patientmanagement.model.Patient;
import com.openclassrooms.patientmanagement.service.impl.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for manage patients in the MediLaboPatient application
 */
@Slf4j
@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * This method send the list of patient
     * @return the list of patients
     */
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getPatients() {
        log.info("GET request on the endpoint /patients: retrieve all patients");
        List<Patient> patients = patientService.getPatients();
        if(patients.isEmpty()){
            log.info("Error getting the list of patients");
            return new ResponseEntity<>(patients, HttpStatus.NOT_FOUND);
        }else{
            log.info("Success getting the list of patients");
            return new ResponseEntity<>(patients, HttpStatus.OK);
        }
    }

    /**
     * This method send a patient from its id
     * @param id Tne id of the patient to retrieve
     * @return the patient retrived
     */
    @GetMapping("/patient/{id}")
    public ResponseEntity<Optional<Patient>> getPatientById(@PathVariable Long id) {
        log.info("GET request on the endpoint /patient/{id}: retrieve an patient from its id");
        Optional<Patient> patient = patientService.getPatientById(id);
        if(!patient.isPresent()){
            log.info("Error getting the patient from the id: {}", id);
            return new ResponseEntity<>(patient, HttpStatus.NOT_FOUND);
        }else{
            log.info("Success getting the patient from the id: {}", id);
            return new ResponseEntity<>(patient, HttpStatus.OK);
        }
    }

    /**
     * This method adds a patient to the repository
     * @param patientToAdd The patient to add
     * @return the person added
     */
    @PostMapping("/patient")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patientToAdd) {
        log.info("POST request on the endpoint /patient: add an patient to the repository");
        Patient patient = patientService.addPatient(patientToAdd);
        if(patient == null){
            log.info("Error adding the patient");
            return new ResponseEntity<>(patient, HttpStatus.NOT_FOUND);
        }else{
            log.info("Success adding the patient");
            return new ResponseEntity<>(patient, HttpStatus.OK);
        }
    }

    /**
     * This method update a patient
     * @param patientToUpdate The patient to update
     * @return the patient updated
     */
    @PutMapping("/patient")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patientToUpdate) {
        log.info("PUT request on the endpoint /patient: update an patient");
        Patient patientUpdated = patientService.updatePatient(patientToUpdate);
        if(patientUpdated == null){
            log.info("Error updating the patient");
            return new ResponseEntity<>(patientUpdated, HttpStatus.NOT_FOUND);
        }else{
            log.info("Success updating the patient");
            return new ResponseEntity<>(patientUpdated, HttpStatus.OK);
        }
    }

    /**
     * This method delete a patient to the repository
     * @param id Tne id of the patient to delete
     * @return the status code
     */
    @DeleteMapping("/patient/{id}")
    public ResponseEntity deletePatientById(@PathVariable Long id) {
        log.info("DELETE request on the endpoint /patient/{id}: delete an patient from its id");
        boolean isDeleted = patientService.deletePatientById(id);
        if(!isDeleted){
            log.info("Error deleting the patient from the id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            log.info("Success deleting the patient from the id: {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
