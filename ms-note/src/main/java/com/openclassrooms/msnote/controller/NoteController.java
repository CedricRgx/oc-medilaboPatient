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

@Slf4j
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

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

    @GetMapping("/id/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") String id){
        log.info("GET request on the endpoint /note/id/{id}: retrieve a note with ID: " + id);
        Optional<Note> note = noteService.getNoteById(id);
        if(note.isEmpty()){
            log.error("No note with ID: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            log.info("Success getting the note with ID: " + id);
            return new ResponseEntity<>(note.get(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable("id") Long patId) {
        log.info("GET request on the endpoint /note/{id}: retrieve all notes for the patient with ID: " + patId);
        List<Note> notes = noteService.getNotesByPatId(patId);
        if(notes.isEmpty()){
            log.warn("No list of notes for the patient with ID: " + patId);
            return new ResponseEntity<>(notes, HttpStatus.NOT_FOUND);
        }else{
            log.info("Success getting the list of notes for the patient with ID: " + patId);
            return new ResponseEntity<>(notes, HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<Note> saveNote(@RequestBody Note noteToAdd) {
        log.info("POST request on the endpoint /note: add a note to the repository");
        Note note = noteService.saveNote(noteToAdd);
        if(note==null){
            log.error("Error adding the note");
            return new ResponseEntity<>(note, HttpStatus.NOT_FOUND);
        }else{
            log.info("Success adding the note");
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") String id, @RequestBody Note updatedNote) {
        log.info("PUT request on the endpoint /note/{id}: update a note with ID: " + id);
        Optional<Note> existingNote = noteService.getNoteById(id);
        if (existingNote.isEmpty()) {
            log.error("Note not found with ID: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Note note = existingNote.get();
            note.setNote(updatedNote.getNote());
            Note savedNote = noteService.saveNote(note);
            log.info("Successfully updated the note with ID: " + id);
            return new ResponseEntity<>(savedNote, HttpStatus.OK);
        }
    }

    /**
     * This method delete a note to the repository
     * @param id Tne id of the note to delete
     * @return the status code
     */
    @PostMapping("/removeNote/{id}")
    public ResponseEntity<Boolean> deleteNoteById(@PathVariable("id") String id) {
        log.info("DELETE request on the endpoint /note/{id}: delete a note from its id");
        boolean isDeleted = noteService.deleteNoteById(id);
        if(!isDeleted){
            log.error("Error deleting the note from the id: {}", id);
            return new ResponseEntity<>(isDeleted, HttpStatus.NOT_FOUND);
        }else {
            log.info("Success deleting the note from the id: {}", id);
            return new ResponseEntity<>(isDeleted, HttpStatus.OK);
        }
    }

}