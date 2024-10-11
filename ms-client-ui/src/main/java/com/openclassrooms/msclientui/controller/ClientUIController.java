package com.openclassrooms.msclientui.controller;


import com.openclassrooms.msclientui.beans.Patient;
import com.openclassrooms.msclientui.service.ClientUIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@Slf4j
public class ClientUIController {

    @Autowired
    private ClientUIService clientUIService;

    @GetMapping("/patientslist")
    public String getAllPatients(Model model) {
        log.info("patientsList");
        List<Patient> patientslist = clientUIService.getPatientsList();
        if(patientslist == null) {
            log.info("The list of patients is null");
        }
        model.addAttribute("patientslist", patientslist);
        return "patientslist";
    }

}