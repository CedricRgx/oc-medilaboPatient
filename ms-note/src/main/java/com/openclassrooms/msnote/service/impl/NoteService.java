package com.openclassrooms.msnote.service.impl;

import com.openclassrooms.msnote.model.Note;
import com.openclassrooms.msnote.proxy.FeignClient;
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

    @Autowired
    private FeignClient feignClient;

    /**
     * Get all notes for all patients.
     *
     * @return List of notes for all patients.
     */
    public List<Note> getAllNotes(){
        log.info("Retrieve all notes for all patients");
        return noteRepository.findAll();
    }

    /**
     * Retrieves a specific note by its ID.
     * @param id The ID of the note.
     * @return Optional containing the note if found, otherwise empty.
     */
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
        log.info("Adding or updating a note for patient ID: {}", note.getPatId());
        Boolean isExist = feignClient.isExist(note.getPatId());
        if (!isExist) {
            log.error("Patient not found with ID during saving or updating operation: {}", note.getPatId());
            return null;
        }
        Note savedNote = noteRepository.save(note);
        log.info("Successfully added or updated a note with ID: {}", savedNote.getId());
        return savedNote;
    }

    /**
     * Deletes a note by its ID.
     * @param id The ID of the note to delete.
     * @return true if the note was deleted, otherwise false.
     */
    public boolean deleteNoteById(String id) {
        log.info("Deleting a note with ID: {}", id);
        try {
            Optional<Note> note = noteRepository.findById(id);
            if (note.isPresent()) {
                noteRepository.deleteById(id);
                return true;
            } else {
                log.warn("Note with ID: {} not found", id);
                return false;
            }
        } catch (Exception e) {
            log.error("Unexpected error occurred while deleting note with ID: {}", id, e);
            return false;
        }
    }

    /**
     * Checks if notes exist for the specified patient ID in the repository.
     *
     * @param patId the ID of the patient to check
     * @return true if notes exist for the given patient ID, false otherwise
     */
    @Override
    public boolean existsByPatId(Long patId) {
        return noteRepository.existsNotesByPatId(patId);
    }

    /**
     * Deletes all notes associated with the specified patient ID.
     *
     * @param patId the ID of the patient whose notes are to be deleted
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteNotesByPatientId(Long patId) {
        log.info("Deleting all notes for patient ID: {}", patId);
        Long count = noteRepository.deleteByPatId(patId);
        if (count > 0) {
            log.info("Successfully deleted notes for patient ID: {}", patId);
            return true;
        }else{
            log.error("Error occurred while deleting notes for patient ID: {}", patId);
            return false;
        }
    }


}
