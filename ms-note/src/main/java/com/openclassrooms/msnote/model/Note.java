package com.openclassrooms.msnote.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {

    /**
     * The unique identifier for the patient
     */
    private Long id;

    private String title;

    private String content;

}