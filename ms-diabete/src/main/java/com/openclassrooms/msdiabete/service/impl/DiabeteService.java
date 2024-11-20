package com.openclassrooms.msdiabete.service.impl;

import com.openclassrooms.msdiabete.model.Note;
import com.openclassrooms.msdiabete.model.Patient;
import com.openclassrooms.msdiabete.proxy.FeignClient;
import com.openclassrooms.msdiabete.service.IDiabeteService;
import com.openclassrooms.msdiabete.util.CalculateAge;
import com.openclassrooms.msdiabete.util.DiabeteRiskLevel;
import com.openclassrooms.msdiabete.util.Gender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.openclassrooms.msdiabete.util.DiabeteRiskLevel.NOT_DEFINED;

/**
 * The DiabeteService class provides business logic related to Diabete logic.
 */
@Service
@Slf4j
public class DiabeteService implements IDiabeteService {

    @Autowired
    private FeignClient feignClient;

    /**
     * List of specific medical terms used to evaluate diabetes risk.
     */
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

    /**
     * Evaluates the diabetes risk level for a patient based on their age, gender,
     * and medical notes.     *
     * Risk levels are defined as follows:
     * - For patients over 30: "BORDERLINE", "IN_DANGER", or "EARLY_ONSET" based on term counts.
     * - For male patients under 30: "IN_DANGER" if terms are between 3-4, "EARLY_ONSET" if 5 or more.
     * - For female patients under 30: "IN_DANGER" if terms are between 4-6, "EARLY_ONSET" if 7 or more.
     *
     * @param patientId the ID of the patient whose diabetes risk is to be evaluated
     * @return a String representing the diabetes risk level, which can be "NONE",
     *         "BORDERLINE", "IN_DANGER", or "EARLY_ONSET"
     */
    public DiabeteRiskLevel evaluateDiabeteRiskLevel(Long patientId) {
        log.info("Evaluate the diabete risk level for the patient with ID: {}", patientId);

        Patient patient = feignClient.getPatientById(patientId);
        if (patient == null) {
            log.warn("No patient found for patient with ID: {}", patientId);
            return null;
        }

        List<Note> patientNotes = feignClient.getNotesByPatientId(patientId);
        if (patientNotes == null) {
            log.warn("No medical notes found for patient with ID: {}", patientId);
            return null;
        }

        boolean isOlderThan30 = new CalculateAge().isOlderThan30(patient.getBirthdate());
        List<String> medicalNotes = patientNotes.stream().map(Note::getNote).toList();
        int count = countTerms(medicalNotes);

        if (count == 0) {
            return DiabeteRiskLevel.NONE;
        }

        if (isOlderThan30) {
            if (count >= 2 && count <= 5) {
                return DiabeteRiskLevel.BORDERLINE;
            } else if (count == 6 || count == 7) {
                return DiabeteRiskLevel.IN_DANGER;
            } else if (count >= 8) {
                return DiabeteRiskLevel.EARLY_ONSET;
            }
        } else {
            if (Gender.M.equals(patient.getGender())) {
                if (count >= 3 && count < 5) {
                    return DiabeteRiskLevel.IN_DANGER;
                } else if (count >= 5) {
                    return DiabeteRiskLevel.EARLY_ONSET;
                }
            } else if (Gender.F.equals(patient.getGender())) {
                if (count >= 4 && count < 7) {
                    return DiabeteRiskLevel.IN_DANGER;
                } else if (count >= 7) {
                    return DiabeteRiskLevel.EARLY_ONSET;
                }
            }
        }
        return NOT_DEFINED;
    }

    /**
     * Counts occurrences of specific medical terms within a list of medical notes.
     *
     * @param medicalNotes a list of medical notes in which to search for specific terms
     * @return the total count of occurrences of the predefined terms across all notes
     */
    private int countTerms(List<String> medicalNotes) {
        log.info("Count the terms in the list of medical notes ignoring case");
        return medicalNotes.stream()
                .mapToInt(medicalNote -> {
                    String lowerCaseNote = medicalNote.toLowerCase();
                    return (int) TERMS.stream()
                            .map(String::toLowerCase)
                            .filter(lowerCaseNote::contains)
                            .count();
                })
                .sum();
    }

}