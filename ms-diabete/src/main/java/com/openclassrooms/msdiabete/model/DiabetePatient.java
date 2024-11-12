package com.openclassrooms.msdiabete.model;

import com.openclassrooms.msdiabete.util.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DiabetePatient {

    private Long id;

    private String firstname;

    private String lastname;

    private Gender gender;

    private LocalDate birthdate;

    private List<Note> medicalNotes;

    private String riskLevel;
}
