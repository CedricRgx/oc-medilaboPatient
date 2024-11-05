package com.openclassrooms.msnote.repository;

import com.openclassrooms.msnote.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> getNotesByPatId(Long patientId);

    Optional<Note> findById(String id);
}
