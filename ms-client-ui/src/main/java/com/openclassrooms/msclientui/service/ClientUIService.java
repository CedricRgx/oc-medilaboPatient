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

import java.util.List;

@Service
@Slf4j
public class ClientUIService {

    private final FeignClient feignClient;

    public ClientUIService(FeignClient feignClient) {
        this.feignClient = feignClient;
    }

    public List<Patient> getPatientsList() {
        return feignClient.getPatientsList();
    }

    public CustomPage<Patient> getPatientsList(int page, int size){
        List<Patient> patientslist = feignClient.getPatientsList();

        int totalPatients = patientslist.size();
        int totalPages = (int) Math.ceil((double) totalPatients / size);
        int start = page * size;
        int end = Math.min((start + size), totalPatients);

        List<Patient> pageContent = patientslist.subList(start, end);
        return new CustomPage<>(pageContent, totalPages, page);
    }

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

    public Patient savePatient(Patient patient){
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

    public boolean deletePatient(Long id){
        boolean isDeleted = feignClient.deletePatient(id);
        return isDeleted;
    }

    public List<Note> getAllNotes(){
        List<Note> notes = feignClient.getNotesList();
        return notes;
    }

    public Note getNoteById(String id){
        Note note = feignClient.getNoteById(id);
        if(note == null) {
            throw new NoteNotFoundException("Note not found with ID: " + id);
        }
        return note;
    }

    public List<Note> getNotesByPatientId(Long patientId){
        log.info("getNoteByPatientId");
        try {
            List<Note> notes = feignClient.getNotesByPatientId(patientId);
            log.info("Notes found for patient with ID: {}", patientId);
            return notes;
        }catch (FeignException.NotFound e){
            throw new NoteNotFoundException("No notes found for patient with ID: " + patientId);
        }

    }

    public Note saveNote(Note note){
        try {
            log.info("Saving note: {}", note);
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

    public Note updateNote(String id, Note note) {
        try {
            log.info("Updating note with ID: {}", id);
            return feignClient.updateNote(id, note);
        } catch (Exception e) {
            log.error("Error while updating note. Error: {}", e.getMessage());
            throw new RuntimeException("Failed to update note. Error: " + e.getMessage(), e);
        }
    }

    public boolean deleteNoteById(String id){
        log.info("deleteNote");
        boolean isDeleted = feignClient.deleteNoteById(id);
        return isDeleted;
    }

}
