package com.openclassrooms.msclientui.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a note for the client ui
 */
@Getter
@Setter
public class Note {

    /**
     * The unique identifier for the note
     */
    private String id;

    /**
     * The ID of patient for the note
     */
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
