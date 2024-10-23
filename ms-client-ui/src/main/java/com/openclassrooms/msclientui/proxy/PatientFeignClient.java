package com.openclassrooms.msclientui.proxy;

import com.openclassrooms.msclientui.model.Patient;
import com.openclassrooms.msclientui.proxy.config.PatientFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign client interface to interact with the Patient microservice through the ms-gateway-server
 */
@FeignClient(name="ms-gateway-server", url="http://localhost:8082", configuration=PatientFeignClientConfig.class)
public interface PatientFeignClient {

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

}
