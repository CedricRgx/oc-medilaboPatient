package com.openclassrooms.msclientui.controller;


import com.openclassrooms.msclientui.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@Slf4j
public class ClientUIController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String MSPATIENT_API_URL = "http://localhost:8081/patient";

    @GetMapping("/patientslist")
    public String getAllPatients(Model model) {
        log.info("GET request for patients list from ms-patient service");

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(System.getenv("MSCLIENTUI_USERNAME"), System.getenv("MSCLIENTUI_PASSWORD"));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List> response = restTemplate.exchange(
                    MSPATIENT_API_URL + "/allpatients",
                    HttpMethod.GET,
                    entity,
                    List.class
            );

            List<Patient> patientslist = response.getBody();
            if (patientslist==null) {
                log.info("The list of patients is null");
            }
            model.addAttribute("patientslist", patientslist);
        } catch (Exception e) {
            log.error("Error retrieving the list of patients: ", e);
            model.addAttribute("error", "Unable to retrieve the list of patients.");
        }
        return "patientslist";
    }
}