package com.openclassrooms.msnote.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Represents a note in the MediLaboPatient application.
 */
@Getter
@Setter
@Document(collection="doctornotes")
public class Note {

    /**
     * The unique identifier for the note
     */
    @Id
    private String id;

    /**
     * The ID of patient for the note
     */
    @Indexed
    private Long patId;

    /**
     * The content of the note
     */
    private String note;

    /**
     * The date of creation of the note
     */
    private LocalDate creationDate;

    /**
     * The date of the last update of the note
     */
    private LocalDate updateDate;

}