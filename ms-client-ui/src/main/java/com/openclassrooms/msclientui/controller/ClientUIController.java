package com.openclassrooms.msclientui.controller;


import com.openclassrooms.msclientui.model.Note;
import com.openclassrooms.msclientui.model.Patient;
import com.openclassrooms.msclientui.service.ClientUIService;
import com.openclassrooms.msclientui.util.CustomPage;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class ClientUIController {

    @Autowired
    private ClientUIService clientUIService;

    @GetMapping("/home")
    public String getAllPatients(Model model,
                                 @RequestParam(name="page", defaultValue = "0") int page,
                                 @RequestParam(name="size", defaultValue = "5") int size) {
                                 //@RequestParam(name="search", required = false) String search) {

        CustomPage<Patient> patientsPage = clientUIService.getPatientsList(page, size);//, search);
        model.addAttribute("patientslist", patientsPage.getContent());
        model.addAttribute("pages", patientsPage.getTotalPages());
        model.addAttribute("currentPage", patientsPage.getCurrentPage());
        model.addAttribute("pageSize", size);
        //model.addAttribute("search", search);

        return "home";
    }

    @GetMapping("/patientslist")
    public String showPatientsList(Model model) {
        return "redirect:/home";
    }


    @GetMapping("/patient/{id}")
    public String getPatientById(@PathVariable Long id, Model model) {
        log.info("getPatientById");
        Patient patient = clientUIService.getPatientById(id);
        if(patient == null) {
            log.error("The patient is null");
        }

        List<Note> notes = clientUIService.getAllNotes();

        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);
        return "patient";
    }

    @GetMapping("/patient/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model) {
        Patient patient = clientUIService.getPatientById(id);
        if (patient == null) {
            log.error("Patient not found with id: " + id);
            return "redirect:/home";
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
    public String savePatient(@Valid @ModelAttribute Patient patient, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            log.error("Validation errors while submitting form.");
            model.addAttribute("patient", patient);
            if (patient.getId() == null) {
                return "addpatient";
            } else {
                return "editpatient";
            }
        }
        try{
            if (patient.getId() == null) {
                log.info("Creating new patient");
                Patient newPatient = clientUIService.savePatient(patient);
                redirectAttributes.addFlashAttribute("successAddPatientMessage", "Success to add the patient.");
                return "redirect:/patient/" + newPatient.getId();
            } else {
                log.info("Updating patient with id: " + patient.getId());
                Patient updatedPatient = clientUIService.savePatient(patient);
                redirectAttributes.addFlashAttribute("successUpdatePatientMessage", "Success to update the patient.");
                return "redirect:/patient/" + updatedPatient.getId();
            }
        }catch(Exception e){
            log.error("Error occurred while saving or updating patient", e);
            if (patient.getId() == null) {
                redirectAttributes.addFlashAttribute("errorAddPatientMessage", "Failed to add the patient.");
                return "redirect:/addpatient";
            }else{
                redirectAttributes.addFlashAttribute("errorUpdatePatientMessage", "Failed to update the patient.");
                return "redirect:/editpatient";
            }
        }
    }

    @PostMapping("/removePatient")
    public String deletePatient(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("deletePatient");
        boolean isDeleted = clientUIService.deletePatient(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("successDeletePatientMessage", "Success to delete the patient.");
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("errorDeletePatientMessage", "Unable to delete the patient. Please try again.");
            return "redirect:/home";
        }
    }

}