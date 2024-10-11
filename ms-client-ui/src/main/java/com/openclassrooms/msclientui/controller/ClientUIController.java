package com.openclassrooms.msclientui.controller;


import com.openclassrooms.msclientui.beans.Patient;
import com.openclassrooms.msclientui.service.ClientUIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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

    @GetMapping("/id")
    public String getPatientById(Model model, Long id) {
        log.info("getPatientById");
        Patient patient = clientUIService.getPatientById(id);
        if(patient == null) {
            log.error("The patient is null");
        }
        model.addAttribute("patient", patient);
        return "id";
    }

    @PostMapping("/save")
    public String savePatient(Model model, Patient patient) {
        log.info("savePatient");
        Patient savedPatient = clientUIService.savePatient(patient);
        if(savedPatient == null) {
            log.error("The patient is null");
        }
        model.addAttribute("patient", savedPatient);
        return "id";
    }

    @PutMapping("/update")
    public String updatePatient(Model model, Patient patient) {
        log.info("updatePatient");
        Patient updatedPatient = clientUIService.updatePatient(patient.getId(), patient);
        if(updatedPatient == null) {
            log.error("The patient is null");
        }
        model.addAttribute("patient", updatedPatient);
        return "id";
    }

    @DeleteMapping("/delete")
    public String deletePatient(Long id) {
        log.info("deletePatient");
        boolean isDeleted = clientUIService.deletePatient(id);
        if(!isDeleted) {
            log.error("The patient is null");
        }
        return "patientslist";
    }

}