package com.openclassrooms.msnote.service.impl;

import com.openclassrooms.msnote.model.Note;
import com.openclassrooms.msnote.repository.NoteRepository;
import com.openclassrooms.msnote.service.INoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The NoteService class provides business logic related to Note entities.
 */
@Slf4j
@Service
public class NoteService implements INoteService {

    @Autowired
    private NoteRepository noteRepository;

    /**
     * Get all notes for all patients.
     *
     * @return List of notes for all patients.
     */
    public List<Note> getAllNotes(){
        log.info("Retrieve all notes for all patients");
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(String id) {
        log.info("Retrieve a note with ID: {}", id);
        try {
            return noteRepository.findById(id);
        } catch (IllegalArgumentException e) {
            log.error("Invalid ID format: {}", id);
            return Optional.empty();
        }
    }


    /**
     * Get all notes associated with a patient ID.
     * @param patId The ID of the patient.
     * @return List of notes for the patient.
     */
    public List<Note> getNotesByPatId(Long patId){
        log.info("Retrieve all notes for the patient with ID: {}", patId);
        return noteRepository.getNotesByPatId(patId);
    }

    /**
     * Adds a new note to the repository.
     * @param note The Note object to be added.
     * @return The added Note object.
     */
    public Note saveNote(Note note) {
        log.info("Adding an note");
        return noteRepository.save(note);
    }

    public boolean deleteNoteById(String id) {
        log.info("Deleting a note with ID: {}", id);
        try {
            noteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Unexpected error occurred while deleting note with ID: {}", id, e);
            return false;
        }
    }


}
