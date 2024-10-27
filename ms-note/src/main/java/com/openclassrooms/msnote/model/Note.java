package com.openclassrooms.msnote.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {

    /**
     * The unique identifier for the note
     */
    private Long id;

    private Long patientId;

    private String title;

    private String content;

}