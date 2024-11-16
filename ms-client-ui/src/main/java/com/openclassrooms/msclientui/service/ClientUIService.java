package com.openclassrooms.msclientui.service;

import com.openclassrooms.msclientui.exception.NoteNotFoundException;
import com.openclassrooms.msclientui.model.Note;
import com.openclassrooms.msclientui.model.Patient;
import com.openclassrooms.msclientui.exception.PatientNotFoundException;
import com.openclassrooms.msclientui.proxy.FeignClient;
import com.openclassrooms.msclientui.util.CustomPage;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Service class for handling patient and note operations.
 */
@Slf4j
@Service
public class ClientUIService {

    private final FeignClient feignClient;

    /**
     * Constructor for ClientUIService, injecting FeignClient.
     *
     * @param feignNote Feign client for communication with other services.
     */
    public ClientUIService(FeignClient feignNote) {
        this.feignClient = feignNote;
    }

    /**
     * Retrieves the complete list of patients.
     *
     * @return A list of all patients.
     */
    public List<Patient> getPatientsList() {
        return feignClient.getPatientsList();
    }

    /**
     * Retrieves a paginated list of patients.
     *
     * @param page The page number to retrieve.
     * @param size The number of patients per page.
     * @return A CustomPage containing the requested page of patients.
     */
    public CustomPage<Patient> getPatientsList(int page, int size) {
        List<Patient> patientslist = feignClient.getPatientsList();

        int totalPatients = patientslist.size();
        int totalPages = (int) Math.ceil((double) totalPatients / size);
        int start = page * size;
        if (start >= totalPatients) {
            return new CustomPage<>(Collections.emptyList(), totalPages, page);
        }

        int end = Math.min((start + size), totalPatients);

        List<Patient> pageContent = patientslist.subList(start, end);
        return new CustomPage<>(pageContent, totalPages, page);
    }

    /**
     * Retrieves a patient by their ID.
     *
     * @param id The ID of the patient.
     * @return The Patient with the specified ID, or null if not found.
     */
    public Patient getPatientById(Long id) {
        try {
            Patient patient = feignClient.getPatientById(id);
            if (patient == null) {
                throw new PatientNotFoundException("Patient not found with ID: " + id);
            }
            return patient;
        } catch (FeignException.InternalServerError e) {
            log.error("Internal server error while fetching patient with ID: {}. Error: {}", id, e.getMessage());
            return null;
        } catch (FeignException e) {
            log.error("Error while fetching patient with ID: {}. Error: {}", id, e.getMessage());
            return null;
        }
    }

    /**
     * Saves a new patient or updates an existing patient.
     *
     * @param patient The patient to save or update.
     * @return The saved or updated Patient.
     * @throws RuntimeException if an error occurs while saving the patient.
     */
    public Patient savePatient(Patient patient) {
        try {
            log.info("Saving patient: {}", patient);
            return feignClient.savePatient(patient);
        } catch (FeignException.BadRequest e) {
            log.error("Error while saving patient. BadRequest: {}", e.responseBody());
            throw new RuntimeException("Failed to save patient. Error: " + e.getMessage(), e);
        } catch (FeignException e) {
            log.error("Error while saving patient. Feign error: {}", e.getMessage());
            throw new RuntimeException("Failed to save patient. Error: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("General error while saving patient. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to save patient. Error: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes a patient by ID.
     *
     * @param id The ID of the patient to delete.
     * @return true if the patient was deleted successfully, false otherwise.
     */
    public boolean deletePatient(Long id) {
        boolean isDeleted = feignClient.deletePatient(id);
        return isDeleted;
    }

    /**
     * Retrieves all notes.
     *
     * @return A list of all notes.
     */
    public List<Note> getAllNotes() {
        List<Note> notes = feignClient.getNotesList();
        return notes;
    }

    /**
     * Retrieves a note by its ID.
     *
     * @param id The ID of the note.
     * @return The Note with the specified ID.
     * @throws NoteNotFoundException if no note is found with the specified ID.
     */
    public Note getNoteById(String id) {
        Note note = feignClient.getNoteById(id);
        if (note == null) {
            throw new NoteNotFoundException("Note not found with ID: " + id);
        }
        return note;
    }

    /**
     * Retrieves all notes for a specific patient by patient ID.
     *
     * @param patientId The ID of the patient.
     * @return A list of notes associated with the specified patient.
     * @throws NoteNotFoundException if no notes are found for the specified patient.
     */
    public List<Note> getNotesByPatientId(Long patientId) {
        log.info("getNoteByPatientId");
        try {
            List<Note> notes = feignClient.getNotesByPatientId(patientId);
            log.info("Notes found for patient with ID: {}", patientId);
            return notes;
        } catch (FeignException.NotFound e) {
            throw new NoteNotFoundException("No notes found for patient with ID: " + patientId);
        }

    }

    /**
     * Saves a new note.
     *
     * @param note The note to save.
     * @return The saved Note.
     * @throws RuntimeException if an error occurs while saving the note.
     */
    public Note saveNote(Note note) {
        try {
            log.info("Saving note: {}", note);
            if (note.getCreationDate() == null) {
                note.setCreationDate(LocalDate.now());
            }
            note.setUpdateDate(LocalDate.now());
            return feignClient.saveNote(note);
        } catch (FeignException.BadRequest e) {
            log.error("Error while saving note. BadRequest: {}", e.responseBody());
            throw new RuntimeException("Failed to save note. Error: " + e.getMessage(), e);
        } catch (FeignException e) {
            log.error("Error while saving note. Feign error: {}", e.getMessage());
            throw new RuntimeException("Failed to save note. Error: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("General error while saving note. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to save note. Error: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing note by its ID.
     *
     * @param note The updated note.
     * @return The updated Note.
     * @throws RuntimeException if an error occurs while updating the note.
     */
    public Note updateNote(String id, Note note) {
        try {
            log.info("Updating note with ID: {}", id);
            note.setUpdateDate(LocalDate.now());
            return feignClient.updateNote(id, note);
        } catch (Exception e) {
            log.error("Error while updating note. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to update note. Error: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes a note by ID.
     *
     * @param id The ID of the note to delete.
     * @return true if the note was deleted successfully, false otherwise.
     */
    public boolean deleteNoteById(String id) {
        log.info("deleteNote");
        boolean isDeleted = feignClient.deleteNoteById(id);
        return isDeleted;
    }

    /**
     * Retrieves the diabetes risk level for a patient with the specified ID.
     *
     * @param id the unique identifier of the patient
     * @return a String representing the diabetes risk level of the patient
     */
    public String getDiabetesRiskLevel(Long id){
        log.info("getDiabetesRiskLevel");
        String diabetesRiskLevel = feignClient.getDiabetesRiskLevel(id);
        return diabetesRiskLevel;
    }

}
