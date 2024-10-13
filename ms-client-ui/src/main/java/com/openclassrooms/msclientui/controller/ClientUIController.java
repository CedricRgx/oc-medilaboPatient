package com.openclassrooms.msclientui.controller;


import com.openclassrooms.msclientui.beans.Patient;
import com.openclassrooms.msclientui.service.ClientUIService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String editPatientForm(@PathVariable Long id, Model model) {
        Patient patient = clientUIService.getPatientById(id);
        if (patient == null) {
            log.error("Patient not found with id: " + id);
            return "redirect:/patientslist";
        }
        model.addAttribute("patient", patient);
        return "editpatient";
    }

    @GetMapping("/patient/add")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addpatient";
    }

    @PostMapping("/patient/save")
    public String savePatient(@Valid @ModelAttribute Patient patient, BindingResult result, Model model) {
        if(result.hasErrors()) {
            log.error("Validation errors while submitting form.");
            model.addAttribute("patient", patient);
            if (patient.getId() == null) {
                return "addpatient";
            } else {
                return "editpatient";
            }
        }

        if (patient.getId() == null) {
            log.info("Creating new patient");
        } else {
            log.info("Updating patient with id: " + patient.getId());
        }
        log.info("Firstname: " + patient.getFirstname());
        log.info("Lastname: " + patient.getLastname());
        log.info("Birthdate: " + patient.getBirthdate());
        log.info("Gender: " + patient.getGender());
        log.info("Address: " + patient.getAddress());
        log.info("Phone: " + patient.getPhone());

        Patient savedPatient = clientUIService.savePatient(patient);

        return "redirect:/patient/" + savedPatient.getId();
    }

//    @PostMapping("/patient/update")
//    public String updatePatient(@Valid @ModelAttribute Patient patient, BindingResult result, Model model) {
//        log.info("updatePatient");
//        if(result.hasErrors()) {
//            log.error("Validation errors while submitting form.");
//            model.addAttribute("patient", patient);
//            return "editpatient";
//        }
//        try {
//            log.info("Updating patient with id: {}", patient.getId());
//            Patient updatedPatient = clientUIService.updatePatient(patient.getId(), patient);
//            model.addAttribute("patient", updatedPatient);
//            return "redirect:/patient/" + updatedPatient.getId();
//        } catch (Exception e) {
//            log.error("Error occurred while updating patient", e);
//            model.addAttribute("errorMessage", "Failed to update patient.");
//            return "editpatient";
//        }
//    }

    @PostMapping("/removePatient")
    public String deletePatient(@RequestParam("id") Long id, Model model) {
        log.info("deletePatient");
        boolean isDeleted = clientUIService.deletePatient(id);
        if (isDeleted) {
            return "redirect:/patientslist"; // Redirection en cas de succès
        } else {
            model.addAttribute("errorMessage", "Unable to delete the patient. Please try again.");
            return "patientslist"; // Retourne à la vue avec un message d'erreur en cas d'échec
        }
    }

}