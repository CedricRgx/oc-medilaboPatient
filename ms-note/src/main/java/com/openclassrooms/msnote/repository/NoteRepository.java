package com.openclassrooms.msnote.repository;

import com.openclassrooms.msnote.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data Mongo repository for the Note entities.
 */
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    /**
     * Get all notes associated with a patient ID.
     * @param patId The ID of the patient.
     * @return List of notes for the patient.
     */
    List<Note> getNotesByPatId(Long patId);

    /**
     * Finds a note by its ID.
     * @param id The ID of the note.
     * @return An Optional containing the note if found, or empty if not.
     */
    Optional<Note> findById(String id);
}
