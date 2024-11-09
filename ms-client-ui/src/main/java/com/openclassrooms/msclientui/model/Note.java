package com.openclassrooms.msclientui.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
     * The date of creation of the note
     */
    private LocalDate creationDate;

    /**
     * The date of the last update of the note
     */
    private LocalDate updateDate;

}
