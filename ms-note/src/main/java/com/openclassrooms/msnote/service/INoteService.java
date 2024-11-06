package com.openclassrooms.msnote.service;

import com.openclassrooms.msnote.model.Note;

import java.util.List;

public interface INoteService {

    /**
     * Get all notes associated with a patient ID.
     * @param id The ID of the patient.
     * @return List of notes for the patient.
     */
    List<Note> getNotesByPatId(Long id);

    /**
     * Adds a new note to the repository.
     * @param note The Note object to be added.
     * @return The added Note object.
     */
    Note saveNote(Note note);
}
