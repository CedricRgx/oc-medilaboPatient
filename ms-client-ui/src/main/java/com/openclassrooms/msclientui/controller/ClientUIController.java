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

/**
 * Controller for managing client UI requests related to patients and notes.
 * Provides methods for managing patients and patient notes.
 */
@Controller
@Slf4j
public class ClientUIController {

    @Autowired
    private ClientUIService clientUIService;

    /**
     * Displays the home page with a paginated list of patients.
     *
     * @param model The model to pass attributes to the view.
     * @param page  The current page number (default is 0).
     * @param size  The number of patients per page (default is 5).
     * @return The "home" view name.
     */
    @GetMapping("/home")
    public String getAllPatients(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size) {

        CustomPage<Patient> patientsPage = clientUIService.getPatientsList(page, size);
        model.addAttribute("patientslist", patientsPage.getContent());
        model.addAttribute("pages", patientsPage.getTotalPages());
        model.addAttribute("currentPage", patientsPage.getCurrentPage());
        model.addAttribute("pageSize", size);

        return "home";
    }

    /**
     * Redirects to the home page displaying the list of patients.
     *
     * @return Redirects to "/home".
     */
    @GetMapping("/patientslist")
    public String showPatientsList() {
        return "redirect:/home";
    }

    /**
     * Retrieves and displays details for a specific patient.
     *
     * @param id                 The ID of the patient.
     * @param model              The model to pass attributes to the view.
     * @param redirectAttributes Attributes for redirection messages.
     * @return The "patient" view name or redirects to "/home" if the patient is not found.
     */
    @GetMapping("/patient/{id}")
    public String getPatientById(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("getPatientById");
        Patient patient = clientUIService.getPatientById(id);
        if (patient == null) {
            log.error("No patient found with id : {}", id);
            redirectAttributes.addFlashAttribute("errorFoundPatientMessage", "This patient doesn't exist.");
            return "redirect:/home";
        }
        model.addAttribute("patient", patient);
        try {
            List<Note> notes = clientUIService.getNotesByPatientId(id);
            if (!notes.isEmpty()) {
                model.addAttribute("notes", notes);
            } else {
                log.warn("No notes found for patient with id: {}", id);
            }
        } catch (NoteNotFoundException e) {
            log.warn("No notes found for patient with id: {}", id);
        }
        try {
            String diabetesRiskLevel = clientUIService.getDiabetesRiskLevel(id);
            if (diabetesRiskLevel != null && !diabetesRiskLevel.isEmpty()) {
                if(diabetesRiskLevel=="NOT_DEFINED"){
                    diabetesRiskLevel = "Not defined";
                }
                model.addAttribute("diabetesRiskLevel", diabetesRiskLevel);
            } else {
                log.warn("No diabetes risk level found for patient with id: {}", id);
            }
        } catch (Exception e) {
            log.warn("Failed to retrieve diabetes risk level for patient with id: {}", id, e);
        }
        return "patient";
    }

    /**
     * Displays the form to edit an existing patient's details.
     *
     * @param id    The ID of the patient to edit.
     * @param model The model to pass attributes to the view.
     * @return The "editpatient" view name or redirects to "/home" if the patient is not found.
     */
    @GetMapping("/patient/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model) {
        Patient patient = clientUIService.getPatientById(id);
        if (patient == null) {
            log.error("Patient not found with id: {}", id);
            return "redirect:/home";
        }
        model.addAttribute("patient", patient);
        return "editpatient";
    }

    /**
     * Displays the form to add a new patient.
     *
     * @param model The model to pass attributes to the view.
     * @return The "addpatient" view name.
     */
    @GetMapping("/patient/add")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addpatient";
    }

    /**
     * Saves a new patient or updates an existing patient's details.
     *
     * @param patient            The patient to save or update.
     * @param result             The binding result for validation.
     * @param model              The model to pass attributes to the view.
     * @param redirectAttributes Attributes for redirection messages.
     * @return Redirects to the patient's details page or to "/home" if saving fails.
     */
    @PostMapping("/patient/save")
    public String savePatient(@Valid @ModelAttribute Patient patient, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            log.error("Validation errors while submitting form during saving patient.");
            model.addAttribute("patient", patient);
            return "addpatient";
        }
        try {
            log.info("Creating new patient");
            Patient newPatient = clientUIService.savePatient(patient);
            redirectAttributes.addFlashAttribute("successAddPatientMessage", "Success to add the patient.");
            return "redirect:/patient/" + newPatient.getId();
        } catch (Exception e) {
            log.error("Error occurred while saving or updating patient", e);
            redirectAttributes.addFlashAttribute("errorAddPatientMessage", "Failed to add the patient.");
            return "redirect:/home";
        }
    }

    /**
     * Updates an existing patient's details.
     *
     * @param patient            The patient to update.
     * @param result             The binding result for validation.
     * @param model              The model to pass attributes to the view.
     * @param redirectAttributes Attributes for redirection messages.
     * @return Redirects to the patient's details page or returns "editpatient" if validation fails.
     */
    @PostMapping("/patient/update")
    public String updatePatient(@Valid @ModelAttribute Patient patient, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            log.error("Validation errors while submitting form during update patient.");
            model.addAttribute("patient", patient);
            return "editpatient";
        }
        try {
            log.info("Updating patient with id: {}", patient.getId());
            Patient updatedPatient = clientUIService.savePatient(patient);
            redirectAttributes.addFlashAttribute("successUpdatePatientMessage", "Success to update the patient.");
            return "redirect:/patient/" + updatedPatient.getId();
        } catch (Exception e) {
            log.error("Error occurred while saving or updating patient", e);
            redirectAttributes.addFlashAttribute("errorUpdatePatientMessage", "Failed to update the patient.");
            return "redirect:/patient/" + patient.getId();
        }
    }

    /**
     * Deletes a patient by ID.
     *
     * @param id                 The ID of the patient to delete.
     * @param redirectAttributes Attributes for redirection messages.
     * @return Redirects to "/home" with success or error message.
     */
    @PostMapping("/removePatient")
    public String deletePatient(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        log.info("deletePatient");
        boolean isDeleted = clientUIService.deletePatient(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("successDeletePatientMessage", "Success to delete the patient.");
        } else {
            redirectAttributes.addFlashAttribute("errorDeletePatientMessage", "Unable to delete the patient. Please try again.");
        }
        return "redirect:/home";
    }

    /**
     * Displays the form to add a note to a patient.
     *
     * @param patientId The ID of the patient for whom the note is to be added.
     * @param model     The model to pass attributes to the view.
     * @return The "addnote" view name.
     */
    @GetMapping("/note/add")
    public String addNoteForm(@RequestParam("patientId") Long patientId, Model model) {
        Patient patient = clientUIService.getPatientById(patientId);
        Note note = new Note();
        note.setPatId(patientId);
        model.addAttribute("patient", patient);
        model.addAttribute("note", note);
        return "addnote";
    }

    /**
     * Saves a new note for a patient.
     *
     * @param note               The note to save.
     * @param redirectAttributes Attributes for redirection messages.
     * @return Redirects to the patient's details page or "/home" if saving fails.
     */
    @PostMapping("/note/save")
    public String saveNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes) {
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

    /**
     * Displays the form to edit an existing note.
     *
     * @param id    The ID of the note to edit.
     * @param model The model to pass attributes to the view.
     * @return The "editnote" view name or redirects to "/home" if the note is not found.
     */
    @GetMapping("/note/edit/{id}")
    public String editNoteForm(@PathVariable String id, Model model) {
        Note note = clientUIService.getNoteById(id);
        if (note == null) {
            log.error("Note not found with id: {}", id);
            return "redirect:/home";
        }
        model.addAttribute("note", note);
        return "editnote";
    }

    /**
     * Updates an existing note's details.
     *
     * @param noteId             The ID of the note to update.
     * @param note               The updated note data.
     * @param redirectAttributes Attributes for redirection messages.
     * @return Redirects to the patient's details page with success or error message.
     */
    @PostMapping("/note/update")
    public String updateNote(@RequestParam("noteId") String noteId, @ModelAttribute Note note, RedirectAttributes redirectAttributes) {
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

    /**
     * Deletes a note by ID.
     *
     * @param id                 The ID of the note to delete.
     * @param redirectAttributes Attributes for redirection messages.
     * @return Redirects to the patient's details page with success or error message.
     */
    @PostMapping("/removeNote")
    public String deleteNote(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
        log.info("deleteNote");
        Note note = clientUIService.getNoteById(id); // for redirect the user to /patient/note.getPatId()
        if (note == null) {
            log.error("Note not found with id: {}", id);
            redirectAttributes.addFlashAttribute("errorDeleteNoteMessage", "Note not found. Unable to delete.");
            return "redirect:/home";
        }

        boolean isDeleted = clientUIService.deleteNoteById(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("successDeleteNoteMessage", "Success to delete the note.");
        } else {
            redirectAttributes.addFlashAttribute("errorDeleteNoteMessage", "Unable to delete the note. Please try again.");
        }
        return "redirect:/patient/" + note.getPatId();
    }

}