package com.openclassrooms.msdiabete.controller;

import com.openclassrooms.msdiabete.service.impl.DiabeteService;
import com.openclassrooms.msdiabete.util.DiabeteRiskLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiabeteControllerTest {

    @Mock
    private DiabeteService diabeteService;

    @InjectMocks
    private DiabeteController diabeteController;

    @Test
    public void testGetDiabeteLevelByPatientId_Success() {
        // Arrange
        Long patientId = 1L;
        String expectedDiabeteLevel = "IN_DANGER";
        when(diabeteService.evaluateDiabeteRiskLevel(patientId)).thenReturn(DiabeteRiskLevel.IN_DANGER);

        // Act
        ResponseEntity<String> response = diabeteController.getDiabeteLevelByPatientId(patientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDiabeteLevel, response.getBody());
        verify(diabeteService).evaluateDiabeteRiskLevel(patientId);
    }

    @Test
    public void testGetDiabeteLevelByPatientId_DiabeteRiskLevelNull() {
        // Arrange
        Long patientId = 2L;
        when(diabeteService.evaluateDiabeteRiskLevel(patientId)).thenReturn(null);

        // Act
        ResponseEntity<String> response = diabeteController.getDiabeteLevelByPatientId(patientId);

        // Assert
        String expectedErrorMessage = "Error getting diabete level for the patient with ID: " + patientId;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(expectedErrorMessage, response.getBody());
        verify(diabeteService).evaluateDiabeteRiskLevel(patientId);
    }

    @Test
    public void testGetDiabeteLevelByPatientId_InternalServerError() {
        // Arrange
        Long patientId = 2L;
        when(diabeteService.evaluateDiabeteRiskLevel(patientId)).thenThrow(new RuntimeException("Unexpected error"));

        // Act
        ResponseEntity<String> response = diabeteController.getDiabeteLevelByPatientId(patientId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error getting diabete level for the patient with ID: " + patientId, response.getBody());
        verify(diabeteService).evaluateDiabeteRiskLevel(patientId);
    }
}
