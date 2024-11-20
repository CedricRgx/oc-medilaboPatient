package com.openclassrooms.msdiabete.service;

import com.openclassrooms.msdiabete.model.Note;
import com.openclassrooms.msdiabete.model.Patient;
import com.openclassrooms.msdiabete.proxy.FeignClient;
import com.openclassrooms.msdiabete.service.impl.DiabeteService;
import com.openclassrooms.msdiabete.util.DiabeteRiskLevel;
import com.openclassrooms.msdiabete.util.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiabeteServiceTest {

    @Mock
    private FeignClient feignClient;

    @InjectMocks
    private DiabeteService diabeteService;

    private Patient malePatientUnder30;
    private Patient femalePatientUnder30;
    private Patient patientOver30;

    @BeforeEach
    public void setUp() {
        malePatientUnder30 = new Patient();
        malePatientUnder30.setId(1L);
        malePatientUnder30.setFirstname("John");
        malePatientUnder30.setLastname("Doe");
        malePatientUnder30.setBirthdate(LocalDate.now().minusYears(25));
        malePatientUnder30.setGender(Gender.M);

        femalePatientUnder30 = new Patient();
        femalePatientUnder30.setId(2L);
        femalePatientUnder30.setFirstname("Jane");
        femalePatientUnder30.setLastname("Smith");
        femalePatientUnder30.setBirthdate(LocalDate.now().minusYears(25));
        femalePatientUnder30.setGender(Gender.F);

        patientOver30 = new Patient();
        patientOver30.setId(3L);
        patientOver30.setFirstname("Alice");
        patientOver30.setLastname("Brown");
        patientOver30.setBirthdate(LocalDate.now().minusYears(40));
        patientOver30.setGender(Gender.F);
    }

    @Test
    public void testEvaluateDiabeteRiskLevel_NoPatientFound() {
        // Arrange
        Long patientId = 1L;
        when(feignClient.getPatientById(patientId)).thenReturn(null);

        // Act
        DiabeteRiskLevel result = diabeteService.evaluateDiabeteRiskLevel(patientId);

        // Assert
        assertNull(result);
        verify(feignClient).getPatientById(patientId);
    }

    @Test
    public void testEvaluateDiabeteRiskLevel_NotDefined() {
        // Arrange
        Long patientId = 1L;
        List<Note> notes = List.of(
                new Note() {{ setNote("Cholestérol"); }}
        );

        when(feignClient.getPatientById(patientId)).thenReturn(patientOver30);
        when(feignClient.getNotesByPatientId(patientId)).thenReturn(notes);

        // Act
        DiabeteRiskLevel result = diabeteService.evaluateDiabeteRiskLevel(patientId);

        // Assert
        assertEquals(DiabeteRiskLevel.NOT_DEFINED, result);
        verify(feignClient).getPatientById(patientId);
        verify(feignClient).getNotesByPatientId(patientId);
    }

    @Test
    public void testEvaluateDiabeteRiskLevel_NoRisk() {
        // Arrange
        List<Note> notes = List.of(
                new Note() {{ setNote("Pomme"); }},
                new Note() {{ setNote("Citron"); }}
        );
        when(feignClient.getPatientById(3L)).thenReturn(patientOver30);
        when(feignClient.getNotesByPatientId(3L)).thenReturn(notes);

        // Act
        DiabeteRiskLevel result = diabeteService.evaluateDiabeteRiskLevel(3L);

        // Assert
        assertEquals(DiabeteRiskLevel.NONE, result);
    }

    @Test
    public void testEvaluateDiabeteRiskLevel_BorderlineRisk() {
        // Arrange
        List<Note> notes = List.of(
                new Note() {{ setNote("Hémoglobine A1C"); }},
                new Note() {{ setNote("Cholestérol"); }},
                new Note() {{ setNote("Poids"); }}
        );
        when(feignClient.getPatientById(3L)).thenReturn(patientOver30);
        when(feignClient.getNotesByPatientId(3L)).thenReturn(notes);

        // Act
        DiabeteRiskLevel result = diabeteService.evaluateDiabeteRiskLevel(3L);

        // Assert
        assertEquals(DiabeteRiskLevel.BORDERLINE, result);
    }

    @Test
    public void testEvaluateDiabeteRiskLevel_InDanger_Over30() {
        // Arrange
        List<Note> notes = List.of(
                new Note() {{ setNote("Hémoglobine A1C"); }},
                new Note() {{ setNote("Cholestérol"); }},
                new Note() {{ setNote("Poids"); }},
                new Note() {{ setNote("Vertiges"); }},
                new Note() {{ setNote("Fumeur"); }},
                new Note() {{ setNote("Microalbumine"); }}
        );
        when(feignClient.getPatientById(3L)).thenReturn(patientOver30);
        when(feignClient.getNotesByPatientId(3L)).thenReturn(notes);

        // Act
        DiabeteRiskLevel result = diabeteService.evaluateDiabeteRiskLevel(3L);

        // Assert
        assertEquals(DiabeteRiskLevel.IN_DANGER, result);
    }

    @Test
    public void testEvaluateDiabeteRiskLevel_EarlyOnset_Over30() {
        // Arrange
        List<Note> notes = List.of(
                new Note() {{ setNote("Hémoglobine A1C"); }},
                new Note() {{ setNote("Cholestérol"); }},
                new Note() {{ setNote("Poids"); }},
                new Note() {{ setNote("Vertiges"); }},
                new Note() {{ setNote("Fumeur"); }},
                new Note() {{ setNote("Microalbumine"); }},
                new Note() {{ setNote("Rechute"); }},
                new Note() {{ setNote("Anticorps"); }}
        );
        when(feignClient.getPatientById(3L)).thenReturn(patientOver30);
        when(feignClient.getNotesByPatientId(3L)).thenReturn(notes);

        // Act
        DiabeteRiskLevel result = diabeteService.evaluateDiabeteRiskLevel(3L);

        // Assert
        assertEquals(DiabeteRiskLevel.EARLY_ONSET, result);
    }

    @Test
    public void testEvaluateDiabeteRiskLevel_InDanger_MaleUnder30() {
        // Arrange
        List<Note> notes = List.of(
                new Note() {{ setNote("Hémoglobine A1C"); }},
                new Note() {{ setNote("Cholestérol"); }},
                new Note() {{ setNote("Poids"); }},
                new Note() {{ setNote("Vertiges"); }}
        );
        when(feignClient.getPatientById(1L)).thenReturn(malePatientUnder30);
        when(feignClient.getNotesByPatientId(1L)).thenReturn(notes);

        // Act
        DiabeteRiskLevel result = diabeteService.evaluateDiabeteRiskLevel(1L);

        // Assert
        assertEquals(DiabeteRiskLevel.IN_DANGER, result);
    }

    @Test
    public void testEvaluateDiabeteRiskLevel_EarlyOnset_MaleUnder30() {
        // Arrange
        List<Note> notes = List.of(
                new Note() {{ setNote("Hémoglobine A1C"); }},
                new Note() {{ setNote("Cholestérol"); }},
                new Note() {{ setNote("Poids"); }},
                new Note() {{ setNote("Vertiges"); }},
                new Note() {{ setNote("Microalbumine"); }}
        );
        when(feignClient.getPatientById(1L)).thenReturn(malePatientUnder30);
        when(feignClient.getNotesByPatientId(1L)).thenReturn(notes);

        // Act
        DiabeteRiskLevel result = diabeteService.evaluateDiabeteRiskLevel(1L);

        // Assert
        assertEquals(DiabeteRiskLevel.EARLY_ONSET, result);
    }

    @Test
    public void testEvaluateDiabeteRiskLevel_InDanger_FemaleUnder30() {
        // Arrange
        List<Note> notes = List.of(
                new Note() {{ setNote("Hémoglobine A1C"); }},
                new Note() {{ setNote("Cholestérol"); }},
                new Note() {{ setNote("Poids"); }},
                new Note() {{ setNote("Vertiges"); }},
                new Note() {{ setNote("Fumeuse"); }}
        );
        when(feignClient.getPatientById(2L)).thenReturn(femalePatientUnder30);
        when(feignClient.getNotesByPatientId(2L)).thenReturn(notes);

        // Act
        DiabeteRiskLevel result = diabeteService.evaluateDiabeteRiskLevel(2L);

        // Assert
        assertEquals(DiabeteRiskLevel.IN_DANGER, result);
    }

    @Test
    public void testEvaluateDiabeteRiskLevel_EarlyOnset_FemaleUnder30() {
        // Arrange
        List<Note> notes = List.of(
                new Note() {{ setNote("Hémoglobine A1C"); }},
                new Note() {{ setNote("Cholestérol"); }},
                new Note() {{ setNote("Poids"); }},
                new Note() {{ setNote("Vertiges"); }},
                new Note() {{ setNote("Fumeuse"); }},
                new Note() {{ setNote("Microalbumine"); }},
                new Note() {{ setNote("Rechute"); }}
        );
        when(feignClient.getPatientById(2L)).thenReturn(femalePatientUnder30);
        when(feignClient.getNotesByPatientId(2L)).thenReturn(notes);

        // Act
        DiabeteRiskLevel result = diabeteService.evaluateDiabeteRiskLevel(2L);

        // Assert
        assertEquals(DiabeteRiskLevel.EARLY_ONSET, result);
    }

    @Test
    public void testEvaluateDiabeteRiskLevel_ReturnsNull() {
        // Arrange
        when(feignClient.getPatientById(3L)).thenReturn(patientOver30);
        when(feignClient.getNotesByPatientId(3L)).thenReturn(null);

        // Act
        DiabeteRiskLevel result = diabeteService.evaluateDiabeteRiskLevel(3L);

        // Assert
        assertEquals(null, result);
    }
}
