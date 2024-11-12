package com.openclassrooms.msclientui.proxy;

import com.openclassrooms.msclientui.model.Note;
import com.openclassrooms.msclientui.model.Patient;
import com.openclassrooms.msclientui.proxy.config.FeignClientConfig;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign client interface to interact with the Patient microservice, the Note microservice and the Diabete microservice through the ms-gateway-server
 */
@org.springframework.cloud.openfeign.FeignClient(name="ms-gateway-server", url="http://localhost:8082", configuration=FeignClientConfig.class)
public interface FeignClient {

    /**
     * Retrieves a list of all patients.
     *
     * @return a list of Patient representing all patients
     */
    @GetMapping("/patient/allpatients")
    List<Patient> getPatientsList();

    /**
     * Retrieves a patient by their ID.
     *
     * @param id the ID of the patient to retrieve
     * @return a Patient object representing the patient with the specified ID
     */
    @GetMapping("/patient/{id}")
    Patient getPatientById(@PathVariable("id") Long id);

    /**
     * Saves a new patient.
     *
     * @param patient the Patient object to save
     * @return the saved Patient object
     */
    @PostMapping("/patient")
    Patient savePatient(@RequestBody Patient patient);

    /**
     * Updates an existing patient.
     *
     * @param patient the Patient object to update
     * @return the updated Patient object
     */
    @PutMapping("/patient")
    Patient updatePatient(@RequestBody Patient patient);

    /**
     * Deletes a patient by their ID.
     *
     * @param id the ID of the patient to delete
     * @return true if the patient was successfully deleted, false otherwise
     */
    @DeleteMapping("/patient/{id}")
    boolean deletePatient(@PathVariable("id") Long id);

    /**
     * Retrieves all notes.
     *
     * @return ResponseEntity containing the list of all notes and HTTP status.
     */
    @GetMapping("/note/allnotes")
    List<Note> getNotesList();

    /**
     * Retrieves a note by its ID.
     *
     * @param patId The ID of the note.
     * @return ResponseEntity containing the note if found, and HTTP status.
     */
    @GetMapping("/note/{id}")
    List<Note> getNotesByPatientId(@PathVariable("id") Long patId);

    /**
     * Retrieves all notes for a specific patient by patient ID.
     *
     * @param id The ID of the patient.
     * @return ResponseEntity containing the list of notes for the patient and HTTP status.
     */
    @GetMapping("/note/id/{id}")
    Note getNoteById(@PathVariable("id") String id);

    /**
     * Saves a new note.
     *
     * @param note the Note object to save
     * @return the saved Note object
     */
    @PostMapping("/note")
    Note saveNote(@RequestBody Note note);

    /**
     * Updates an existing note.
     *
     * @param id   the ID of the note to update
     * @param note the Note object with updated information
     * @return the updated Note object
     */
    @PutMapping("/note/{id}")
    Note updateNote(@PathVariable("id") String id, @RequestBody Note note);

    /**
     * Deletes a note by their ID.
     *
     * @param id the ID of the note to delete
     * @return true if the note was successfully deleted, false otherwise
     */
    @PostMapping("/note/removeNote/{id}")
    boolean deleteNoteById(@PathVariable("id") String id);


}
