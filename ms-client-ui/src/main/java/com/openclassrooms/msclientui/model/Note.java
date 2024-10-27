package com.openclassrooms.msclientui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {

    private Long id;

    private Long patientId;

    private String title;

    private String content;

}
