package com.openclassrooms.msdiabete.controller;

import com.openclassrooms.msdiabete.service.DiabeteService;
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

    @GetMapping("/{id}")
    public ResponseEntity<String> getDiabeteLevelByPatientId(@PathVariable("id") Long patId) {
        log.info("GET request on the endpoint /diabete/{id}: retrieve diabete level for the patient with ID: {}", patId);
        String DiabeteLevel = diabeteService.evaluateDiabeteRisk(patId);
        return new ResponseEntity<>(DiabeteLevel, HttpStatus.OK);
    }

}