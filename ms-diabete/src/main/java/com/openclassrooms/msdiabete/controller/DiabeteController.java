package com.openclassrooms.msdiabete.controller;

import com.openclassrooms.msdiabete.service.impl.DiabeteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/diabete")
public class DiabeteController {

    @Autowired
    private DiabeteService diabeteService;

    /**
     * Retrieves the diabetes risk level for a patient by their ID.
     *
     * @param patId the ID of the patient whose diabetes level is to be retrieved
     * @return ResponseEntity containing the diabetes level as a String and an HTTP status code
     */
    @GetMapping("/{id}")
    public ResponseEntity<String> getDiabeteLevelByPatientId(@PathVariable("id") Long patId) {
        log.info("GET request on the endpoint /diabete/{id}: retrieve diabete level for the patient with ID: {}", patId);
        try{
            String diabeteLevel = diabeteService.evaluateDiabeteRiskLevel(patId).name();
            if(diabeteLevel == null){
                log.error("No diabete level for the patient with ID: {}", patId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No diabete level for the patient with ID: " + patId);
            }
            return new ResponseEntity<>(diabeteLevel, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error getting diabete level for the patient with ID: {}", patId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting diabete level for the patient with ID: " + patId);
        }
    }

}