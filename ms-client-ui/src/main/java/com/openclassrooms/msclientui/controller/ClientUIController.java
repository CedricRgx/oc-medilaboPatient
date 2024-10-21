package com.openclassrooms.msclientui.controller;


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
        model.addAttribute("patient", patient);
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
            model.addAttribute("successDeletePatientMessage", "Success to delete the patient.");
            return "redirect:/home";
        } else {
            model.addAttribute("errorDeletePatientMessage", "Unable to delete the patient. Please try again.");
            return "home"; // Retourne à la vue avec un message d'erreur en cas d'échec
        }
    }

}