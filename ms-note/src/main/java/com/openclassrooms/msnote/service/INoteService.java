package com.openclassrooms.msnote.service;

import com.openclassrooms.msnote.model.Note;

import java.util.List;
import java.util.Optional;

/**
 * Interface INoteService
 * Provides operations to manage patient notes.
 */
public interface INoteService {

    /**
     * Get all notes for all patients.
     *
     * @return List of notes for all patients.
     */
    List<Note> getAllNotes();

    /**
     * Retrieves a specific note by its ID.
     * @param id The ID of the note.
     * @return Optional containing the note if found, otherwise empty.
     */
    Optional<Note> getNoteById(String id);

    /**
     * Get all notes associated with a patient ID.
     * @param patId The ID of the patient.
     * @return List of notes for the patient.
     */
    List<Note> getNotesByPatId(Long patId);

    /**
     * Adds a new note to the repository.
     * @param note The Note object to be added.
     * @return The added Note object.
     */
    Note saveNote(Note note);

    /**
     * Deletes a note by its ID.
     * @param id The ID of the note to delete.
     * @return true if the note was deleted, otherwise false.
     */
    boolean deleteNoteById(String id);
}
