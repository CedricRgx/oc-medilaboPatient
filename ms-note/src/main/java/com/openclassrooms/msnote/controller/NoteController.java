package com.openclassrooms.msnote.controller;

import com.openclassrooms.msnote.model.Note;
import com.openclassrooms.msnote.service.impl.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing notes.
 */
@Slf4j
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    /**
     * Retrieves all notes.
     *
     * @return ResponseEntity containing the list of all notes and HTTP status.
     */
    @GetMapping("/allnotes")
    public ResponseEntity<List<Note>> getPatients() {
        log.info("GET request on the endpoint /note/allnotes: retrieve all notes");
        List<Note> notes = noteService.getAllNotes();
        if(notes.isEmpty()){
            log.error("Error getting the list of notes");
            return new ResponseEntity<>(notes, HttpStatus.NOT_FOUND);
        }else{
            log.info("Success getting the list of notes");
            return new ResponseEntity<>(notes, HttpStatus.OK);
        }
    }

    /**
     * Retrieves a note by its ID.
     *
     * @param id The ID of the note.
     * @return ResponseEntity containing the note if found, and HTTP status.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") String id){
        log.info("GET request on the endpoint /note/id/{id}: retrieve a note with ID: {}", id);
        Optional<Note> note = noteService.getNoteById(id);
        if(note.isEmpty()){
            log.error("No note with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            log.info("Success getting the note with ID: {}", id);
            return new ResponseEntity<>(note.get(), HttpStatus.OK);
        }
    }

    /**
     * Retrieves all notes for a specific patient by patient ID.
     *
     * @param patId The ID of the patient.
     * @return ResponseEntity containing the list of notes for the patient and HTTP status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable("id") Long patId) {
        log.info("GET request on the endpoint /note/{id}: retrieve all notes for the patient with ID: {}", patId);
        List<Note> notes = noteService.getNotesByPatId(patId);
        if(notes.isEmpty()){
            log.warn("No list of notes for the patient with ID: {}", patId);
            return new ResponseEntity<>(notes, HttpStatus.OK);
        }else{
            log.info("Success getting the list of notes for the patient with ID: {}", patId);
            return new ResponseEntity<>(notes, HttpStatus.OK);
        }
    }

    /**
     * Checks if notes exist for a given patient ID.
     *
     * @param patientId the ID of the patient
     * @return true if notes exist, false otherwise
     */
    @GetMapping("/existsNotesByPatientId/{patientId}")
    public ResponseEntity<Boolean> existsNotesByPatientId(@PathVariable("patientId") Long patientId) {
        log.info("GET request on the endpoint /existsByPatId/{patientId}: checking existence of notes for patient ID: {}", patientId);
        boolean exists = noteService.existsByPatId(patientId);
        if(exists){
            log.info("Notes exist for patient ID: {}", patientId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            log.info("Notes do not exist for patient ID: {}", patientId);
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    /**
     * Adds a new note to the repository.
     *
     * @param noteToAdd The Note object to be added.
     * @return ResponseEntity containing the added note and HTTP status.
     */
    @PostMapping()
    public ResponseEntity<Note> saveNote(@RequestBody Note noteToAdd) {
        log.info("POST request on the endpoint /note: add a note to the repository");
        Note note = noteService.saveNote(noteToAdd);
        if(note==null){
            log.error("Error adding the note");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
            log.info("Success adding the note");
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
    }

    /**
     * Updates an existing note by its ID.
     *
     * @param id The ID of the note to update.
     * @param updatedNote The Note object containing updated information.
     * @return ResponseEntity containing the updated note and HTTP status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") String id, @RequestBody Note updatedNote) {
        log.info("PUT request on the endpoint /note/{id}: update a note with ID: {}", id);
        Optional<Note> existingNote = noteService.getNoteById(id);
        if (existingNote.isEmpty()) {
            log.error("Note not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Note note = existingNote.get();
            note.setNote(updatedNote.getNote());
            note.setUpdateDate(updatedNote.getUpdateDate());
            Note savedNote = noteService.saveNote(note);
            log.info("Successfully updated the note with ID: {}", id);
            return new ResponseEntity<>(savedNote, HttpStatus.OK);
        }
    }

    /**
     * Deletes a note by its ID.
     *
     * @param id The ID of the note to delete.
     * @return ResponseEntity indicating if the deletion is successful and HTTP status.
     */
    @PostMapping("/removeNote/{id}")
    public ResponseEntity<Boolean> deleteNoteById(@PathVariable("id") String id) {
        log.info("DELETE request on the endpoint /note/{id}: delete a note from its id");
        boolean isDeleted = noteService.deleteNoteById(id);
        if(!isDeleted){
            log.error("Error deleting the note from the id: {}", id);
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }else {
            log.info("Success deleting the note from the id: {}", id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }

    /**
     * Deletes all notes associated with the specified patient ID.
     *
     * @param patientId the ID of the patient whose notes are to be deleted
     * @return a ResponseEntity containing a Boolean indicating the success of the deletion
     *         and the appropriate HTTP status code (200 if successful, 404 if no notes found)
     */
    @DeleteMapping("/removeNoteByPatId/{patientId}")
    public ResponseEntity<Boolean> deleteNotesByPatientId(@PathVariable("patientId") Long patientId) {
        log.info("DELETE request on the endpoint /note/patient/{patientId}: delete all notes for the patient with ID: {}", patientId);
        boolean isDeleted = noteService.deleteNotesByPatientId(patientId);
        if(!isDeleted){
            log.error("Error deleting the note from the patient id: {}", patientId);
            return new ResponseEntity<>(false, HttpStatus.OK);
        }else {
            log.info("Success deleting the note from the patient id: {}", patientId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }

}