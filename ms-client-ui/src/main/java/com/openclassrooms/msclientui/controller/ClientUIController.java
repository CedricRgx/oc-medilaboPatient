package com.openclassrooms.msclientui.controller;


import com.openclassrooms.msclientui.exception.NoteNotFoundException;
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
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "5") int size) {

        CustomPage<Patient> patientsPage = clientUIService.getPatientsList(page, size);
        model.addAttribute("patientslist", patientsPage.getContent());
        model.addAttribute("pages", patientsPage.getTotalPages());
        model.addAttribute("currentPage", patientsPage.getCurrentPage());
        model.addAttribute("pageSize", size);

        return "home";
    }

    @GetMapping("/patientslist")
    public String showPatientsList(Model model) {
        return "redirect:/home";
    }


    @GetMapping("/patient/{id}")
    public String getPatientById(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("getPatientById");

        Patient patient = clientUIService.getPatientById(id);
        if (patient == null) {
            log.error("No patient found with id : " + id);
            redirectAttributes.addFlashAttribute("errorFoundPatientMessage", "This patient doesn't exist.");
            return "redirect:/home";
        }
        model.addAttribute("patient", patient);

        try {
            List<Note> notes = clientUIService.getNotesByPatientId(id);
            if (!notes.isEmpty()) {
                model.addAttribute("notes", notes);
            } else {
                log.error("No notes found for patient with id: " + id);
            }
        } catch (NoteNotFoundException e) {
            log.error("No notes found for patient with id: " + id);
        }

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
        if (result.hasErrors()) {
            log.error("Validation errors while submitting form.");
            model.addAttribute("patient", patient);
            if (patient.getId() == null) {
                return "addpatient";
            } else {
                return "editpatient";
            }
        }
        try {
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
        } catch (Exception e) {
            log.error("Error occurred while saving or updating patient", e);
            if (patient.getId() == null) {
                redirectAttributes.addFlashAttribute("errorAddPatientMessage", "Failed to add the patient.");
                return "redirect:/home";
            } else {
                redirectAttributes.addFlashAttribute("errorUpdatePatientMessage", "Failed to update the patient.");
                return "redirect:/patient/" + patient.getId();
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

    @GetMapping("/note/add")
    public String addNoteForm(@RequestParam("patientId") Long patientId, Model model) {
        Patient patient = clientUIService.getPatientById(patientId);
        Note note = new Note();
        note.setPatId(patientId);
        model.addAttribute("patient", patient);
        model.addAttribute("note", note);
        return "addnote";
    }

    @PostMapping("/note/save")
    public String saveNote(@ModelAttribute Note note, Model model, RedirectAttributes redirectAttributes) {
        try {
            log.info("Creating new note");
            Note newNote = clientUIService.saveNote(note);
            redirectAttributes.addFlashAttribute("successAddNoteMessage", "Success to add the note.");
            return "redirect:/patient/" + newNote.getPatId();
        } catch (Exception e) {
            log.error("Error occurred while saving the note", e);
            redirectAttributes.addFlashAttribute("errorAddNoteMessage", "Failed to add the note.");
            return "redirect:/patient/" + note.getPatId();
        }
    }

    @GetMapping("/note/edit/{id}")
    public String editNoteForm(@PathVariable String id, Model model) {
        Note note = clientUIService.getNoteById(id);
        if(note == null) {
            log.error("note not found with id: " + id);
            return "redirect:/home";
        }
//        Patient patient = clientUIService.getPatientById(note.getPatId());
//        model.addAttribute("patient", patient);
        model.addAttribute("note", note);
        return "editnote";
    }

    @PostMapping("/note/update")
    public String updateNote(@RequestParam("noteId") String noteId, @ModelAttribute Note note, Model model, RedirectAttributes redirectAttributes) {
        try {
            log.info("Updating note with ID: {}", noteId);
            Note updatedNote = clientUIService.updateNote(noteId, note);
            redirectAttributes.addFlashAttribute("successUpdateNoteMessage", "Successfully updated the note.");
            return "redirect:/patient/" + updatedNote.getPatId();
        } catch (Exception e) {
            log.error("Error occurred while updating note. Error: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorUpdateNoteMessage", "Failed to update the note.");
            return "redirect:/patient/" + note.getPatId();
        }
    }

    @PostMapping("/removeNote")
    public String deleteNote(@RequestParam("id") String id, Model model, RedirectAttributes redirectAttributes) {
        log.info("deleteNote");
        Note note = clientUIService.getNoteById(id); // for redirect the user to /patient/note.getPatId()
        boolean isDeleted = clientUIService.deleteNoteById(id);
        if(isDeleted){
            redirectAttributes.addFlashAttribute("successDeleteNoteMessage", "Success to delete the note.");
        }else{
            redirectAttributes.addFlashAttribute("errorDeleteNoteMessage", "Unable to delete the note. Please try again.");
        }
        return "redirect:/patient/" + note.getPatId();
    }

}