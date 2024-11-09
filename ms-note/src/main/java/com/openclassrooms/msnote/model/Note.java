package com.openclassrooms.msnote.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
     * The name of the patient
     */
    private String patient;

}