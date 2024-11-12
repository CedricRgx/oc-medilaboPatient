package com.openclassrooms.msdiabete.service;

import com.openclassrooms.msdiabete.model.Note;
import com.openclassrooms.msdiabete.model.Patient;
import com.openclassrooms.msdiabete.proxy.FeignClient;
import com.openclassrooms.msdiabete.util.CalculateAge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DiabeteService {

    @Autowired
    private FeignClient feignClient;

    private static final List<String> TERMS = Arrays.asList(
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fumeur",
            "Fumeuse",
            "Anormal",
            "Cholestérol",
            "Vertiges",
            "Rechute",
            "Réaction",
            "Anticorps"
    );

    public String evaluateDiabeteRisk(Long patientId) {
        Patient patient = feignClient.getPatientById(patientId);
        List<Note> patientNotes = feignClient.getNotesByPatientId(patientId);

        int age = new CalculateAge().getAge(patient.getBirthdate());
        List<String> medicalNotes = patientNotes.stream().map(Note::getNote).toList();
        int count = countTerms(medicalNotes);

        log.info("Patient ID: {}", patientId);
        log.info("Nombre de notes: {}", patientNotes.size());
        log.info("Âge: {}", age);
        log.info("Nombre de termes trouvés: {}", count);

        if (count == 0) {
            return "NONE";
        }

        if (age > 30) {
            if (count >= 2 && count <= 5) {
                return "BORDERLINE";
            } else if (count == 6 || count == 7) {
                return "IN_DANGER";
            } else if (count >= 8) {
                return "EARLY_ONSET";
            }
        } else {
            if ("M".equals(patient.getGender())) {
                if (count >= 3 && count < 5) {
                    return "IN_DANGER";
                } else if (count >= 5) {
                    return "EARLY_ONSET";
                }
            } else if ("F".equals(patient.getGender())) {
                if (count >= 4 && count < 7) {
                    return "IN_DANGER";
                } else if (count >= 7) {
                    return "EARLY_ONSET";
                }
            }
        }
        return "NONE";
    }

    private int countTerms(List<String> medicalNotes) {
        return medicalNotes.stream()
                .mapToInt(medicalNote -> (int) TERMS.stream()
                        .filter(medicalNote::contains)
                        .count())
                .sum();
    }

}