package com.openclassrooms.mspatient.proxy;

import com.openclassrooms.mspatient.proxy.config.FeignClientConfig;
import org.springframework.web.bind.annotation.*;


/**
 * Feign client interface to interact with the Note microservice through the ms-gateway-server
 */
@org.springframework.cloud.openfeign.FeignClient(name="ms-gateway-server", url="${feign.client.ms-gateway.url}", configuration=FeignClientConfig.class)
public interface FeignClient {

    /**
     * Checks if any notes exist for the specified patient ID.
     *
     * @param patientId the ID of the patient to check for notes
     * @return true if notes exist for the given patient ID, false otherwise
     */
    @GetMapping("/note/existsNotesByPatientId/{patientId}")
    boolean existsNotesByPatientId(@PathVariable("patientId") Long patientId);

    /**
     * Deletes a note from the patient ID.
     *
     * @param patientId the ID of the patient
     * @return true if the note was successfully deleted, false otherwise
     */
    @DeleteMapping("/note/removeNoteByPatId/{patientId}")
    boolean deleteNotesByPatientId(@PathVariable("patientId") Long patientId);

}
