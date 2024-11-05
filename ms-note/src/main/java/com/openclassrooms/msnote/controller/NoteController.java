package com.openclassrooms.msnote.controller;

import com.openclassrooms.msnote.model.Note;
import com.openclassrooms.msnote.service.impl.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping("/test/{id}")
//    public ResponseEntity<Optional<Note>> getNotesTest(@PathVariable String id) {
//        log.info("TEST");
//        Optional<Note> note = noteService.getNoteById(id);
//        if(note.isEmpty()){
//            log.error("TEST NOT_FOUND");
//            return new ResponseEntity<>(note, HttpStatus.NOT_FOUND);
//        }else{
//            log.error("TEST OK");
//            return new ResponseEntity<>(note, HttpStatus.OK);
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable("id") Long patId) {
        log.info("GET request on the endpoint /note/{id}: retrieve all notes for the patient with ID: " + patId);
        List<Note> notes = noteService.getNotesByPatId(patId);
        if(notes.isEmpty()){
            log.error("No list of notes for the patient with ID: " + patId);
            return new ResponseEntity<>(notes, HttpStatus.NOT_FOUND);
        }else{
            log.info("Success getting the list of notes for the patient with ID: " + patId);
            return new ResponseEntity<>(notes, HttpStatus.OK);
        }
    }


}
