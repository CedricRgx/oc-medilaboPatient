package com.openclassrooms.mspatient.controller;

import com.openclassrooms.mspatient.model.Patient;
import com.openclassrooms.mspatient.service.impl.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;
    private Patient patient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient();
        patient.setId(1L);
        patient.setFirstname("John");
        patient.setLastname("Doe");
    }

    @Test
    public void testGetPatients() {
        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        when(patientService.getPatients()).thenReturn(patients);

        // Act
        ResponseEntity<List<Patient>> response = patientController.getPatients();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetPatientsNotFound() {
        // Arrange
        when(patientService.getPatients()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<Patient>> response = patientController.getPatients();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testGetPatientById() {
        // Arrange
        when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));

        // Act
        ResponseEntity<Optional<Patient>> response = patientController.getPatientById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().isPresent());
    }

    @Test
    public void testGetPatientByIdNotFound() {
        // Arrange
        when(patientService.getPatientById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Optional<Patient>> response = patientController.getPatientById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(false, response.getBody().isPresent());
    }

    @Test
    public void isExist_ShouldReturnOkAndTrue_WhenPatientExists() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        when(patientService.getPatientById(patientId)).thenReturn(Optional.of(patient));

        // Act
        ResponseEntity<Boolean> response = patientController.isExist(patientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    public void isExist_ShouldReturnOkAndFalse_WhenPatientDoesNotExist() {
        // Arrange
        Long patientId = 1L;
        when(patientService.getPatientById(patientId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Boolean> response = patientController.isExist(patientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    public void testAddPatient() {
        // Arrange
        when(patientService.savePatient(any(Patient.class))).thenReturn(patient);

        // Act
        ResponseEntity<Patient> response = patientController.savePatient(patient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patient, response.getBody());
    }

    @Test
    public void testAddPatientError() {
        // Arrange
        when(patientService.savePatient(any(Patient.class))).thenReturn(null);

        // Act
        ResponseEntity<Patient> response = patientController.savePatient(patient);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdatePatient() {
        // Arrange
        when(patientService.updatePatient(any(Patient.class))).thenReturn(patient);

        // Act
        ResponseEntity<Patient> response = patientController.updatePatient(patient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patient, response.getBody());
    }

    @Test
    public void testUpdatePatientError() {
        // Arrange
        when(patientService.updatePatient(any(Patient.class))).thenReturn(null);

        // Act
        ResponseEntity<Patient> response = patientController.updatePatient(patient);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeletePatientById() {
        // Arrange
        when(patientService.deletePatientById(1L)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = patientController.deletePatientById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeletePatientByIdError() {
        // Arrange
        when(patientService.deletePatientById(1L)).thenReturn(false);

        // Act
        ResponseEntity<Boolean> response = patientController.deletePatientById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deletePatientById_ShouldReturnOkAndTrue_WhenDeletionIsSuccessful() {
        // Arrange
        Long patientId = 1L;
        when(patientService.deletePatientById(patientId)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = patientController.deletePatientById(patientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(patientService, times(1)).deletePatientById(patientId);
    }

    @Test
    public void deletePatientById_ShouldReturnOkAndFalse_WhenPatientNotFound() {
        // Arrange
        Long patientId = 1L;
        when(patientService.deletePatientById(patientId)).thenThrow(new EmptyResultDataAccessException(1));

        // Act
        ResponseEntity<Boolean> response = patientController.deletePatientById(patientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(patientService, times(1)).deletePatientById(patientId);
    }

    @Test
    public void deletePatientById_ShouldReturnInternalServerError_WhenUnexpectedExceptionOccurs() {
        // Arrange
        Long patientId = 1L;
        when(patientService.deletePatientById(patientId)).thenThrow(new RuntimeException("Unexpected error"));

        // Act
        ResponseEntity<Boolean> response = patientController.deletePatientById(patientId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(patientService, times(1)).deletePatientById(patientId);
    }
}