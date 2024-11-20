package com.openclassrooms.msdiabete.proxy;

import com.openclassrooms.msdiabete.model.Note;
import com.openclassrooms.msdiabete.model.Patient;
import com.openclassrooms.msdiabete.proxy.config.FeignClientConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Feign client interface to interact with the Patient microservice and the Note microservice through the ms-gateway-server
 */
@org.springframework.cloud.openfeign.FeignClient(name="ms-gateway-server", url="http://localhost:8082", configuration= FeignClientConfig.class)
public interface FeignClient {

    /**
     * Retrieves a patient by their ID.
     *
     * @param id the ID of the patient to retrieve
     * @return a Patient object representing the patient with the specified ID
     */
    @GetMapping("/patient/{id}")
    Patient getPatientById(@PathVariable("id") Long id);

    /**
     * Retrieves a list of notes of a specific patient.
     *
     * @param patId The ID of the patient whose notes are to be retrieved.
     * @return A list of Note objects associated with the given patient ID.
     */
    @GetMapping("/note/{id}")
    List<Note> getNotesByPatientId(@PathVariable("id") Long patId);

}
