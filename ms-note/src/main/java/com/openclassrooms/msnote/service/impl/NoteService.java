package com.openclassrooms.msnote.service.impl;

import com.openclassrooms.msnote.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    public List<Note> getAllNotes(){

        Note note1 = new Note();
        note1.setId(1L);
        note1.setTitle("Title of note 1");
        note1.setContent("Content of note 1");

        Note note2 = new Note();
        note2.setId(2L);
        note2.setTitle("Title of note 2");
        note2.setContent("Content of note 2");

        return List.of(note1, note2);

    }
}
