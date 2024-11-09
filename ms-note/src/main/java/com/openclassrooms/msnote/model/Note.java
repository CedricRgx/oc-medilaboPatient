package com.openclassrooms.msnote.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

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
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    /**
     * The date of the last update of the note
     */
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate updateDate;

}