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
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientUIService {

    private final FeignClient feignClient;

    //private final NoteFeignClient noteFeignClient;

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
        Patient patient = feignClient.getPatientById(id);
        if (patient == null) {
            throw new PatientNotFoundException("Patient not found with ID: " + id);
        }
        return patient;
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

    public Note getNoteById(Long patientId){

        List<Note> notes = getAllNotes();

        List<Note> newListOfOneNote = notes.stream()
                .filter(note -> note.getPatientId().equals(patientId))
                .collect(Collectors.toList());

        if (newListOfOneNote.isEmpty()) {
            return null;
        }
        return newListOfOneNote.get(0);

    }

}
