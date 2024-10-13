package com.openclassrooms.msclientui.controller;


import com.openclassrooms.msclientui.beans.Patient;
import com.openclassrooms.msclientui.service.ClientUIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    @GetMapping("/patient/{id}")
    public String getPatientById(@PathVariable Long id, Model model) {
        log.info("getPatientById");
        Patient patient = clientUIService.getPatientById(id);
        if(patient == null) {
            log.error("The patient is null");
        }
        model.addAttribute("patient", patient);
        return "patient";
    }

    @GetMapping("/patient/edit/{id}")
    public String showEditPatientForm(@PathVariable Long id, Model model) {
        Patient patient = clientUIService.getPatientById(id);
        if (patient == null) {
            log.error("Patient not found with id: " + id);
            return "redirect:/patientslist";
        }
        model.addAttribute("patient", patient);
        return "editpatient";
    }

    @PostMapping("/patient/save")
    public String savePatient(@ModelAttribute Patient patient) {
        log.info("Updating patient with id: " + patient.getId());
        log.info("Patient ID: " + patient.getId());
        log.info("Patient Birthdate: " + patient.getBirthdate());
        Patient savedPatient = clientUIService.savePatient(patient);
        return "redirect:/patient/"+savedPatient.getId();
    }

    @PutMapping("/patient/update")
    public String updatePatient(@ModelAttribute Patient patient, Model model) {
        log.info("updatePatient");
        Patient updatedPatient = clientUIService.updatePatient(patient.getId(), patient);
        if(updatedPatient == null) {
            log.error("The patient is null");
        }
        model.addAttribute("patient", updatedPatient);
        return "redirect:/patient/" + patient.getId();
    }

    @DeleteMapping("/delete")
    public String deletePatient(Long id) {
        log.info("deletePatient");
        boolean isDeleted = clientUIService.deletePatient(id);
        if(!isDeleted) {
            log.error("The patient is null");
        }
        return "patientslist"; // TODO: method has to be implemented and fixed
    }

}