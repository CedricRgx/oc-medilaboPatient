package com.openclassrooms.msclientui.proxy;

import com.openclassrooms.msclientui.model.Note;
import com.openclassrooms.msclientui.model.Patient;
import com.openclassrooms.msclientui.proxy.config.FeignClientConfig;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign client interface to interact with the Patient microservice through the ms-gateway-server
 */
@org.springframework.cloud.openfeign.FeignClient(name="ms-gateway-server", url="http://localhost:8082", configuration=FeignClientConfig.class)
public interface FeignClient {

    /**
     * Retrieves a list of all patients.
     *
     * @return a list of Patient representing all patients
     */
    @GetMapping("/patient/allpatients")
    List<Patient> getPatientsList();

    /**
     * Retrieves a patient by their ID.
     *
     * @param id the ID of the patient to retrieve
     * @return a Patient object representing the patient with the specified ID
     */
    @GetMapping("/patient/{id}")
    Patient getPatientById(@PathVariable("id") Long id);

    /**
     * Saves a new patient.
     *
     * @param patient the Patient object to save
     * @return the saved Patient object
     */
    @PostMapping("/patient")
    Patient savePatient(@RequestBody Patient patient);

    /**
     * Updates an existing patient.
     *
     * @param patient the Patient object to update
     * @return the updated Patient object
     */
    @PutMapping("/patient")
    Patient updatePatient(@RequestBody Patient patient);

    /**
     * Deletes a patient by their ID.
     *
     * @param id the ID of the patient to delete
     * @return true if the patient was successfully deleted, false otherwise
     */
    @DeleteMapping("/patient/{id}")
    boolean deletePatient(@PathVariable("id") Long id);

    @GetMapping("/note/allnotes")
    List<Note> getNotesList();

    @GetMapping("/note/{id}")
    List<Note> getNotesByPatientId(@PathVariable("id") Long patId);

}
