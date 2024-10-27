package com.openclassrooms.msnote.controller;

import com.openclassrooms.msnote.model.Note;
import com.openclassrooms.msnote.service.impl.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
