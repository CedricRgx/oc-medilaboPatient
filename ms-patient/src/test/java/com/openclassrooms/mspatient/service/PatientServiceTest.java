package com.openclassrooms.mspatient.service;

import com.openclassrooms.mspatient.exceptions.PatientNotFoundException;
import com.openclassrooms.mspatient.model.Patient;
import com.openclassrooms.mspatient.repository.PatientRepository;
import com.openclassrooms.mspatient.service.impl.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

//    @Mock
//    private FeignClient feignClient;

    private Patient patient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient();
        patient.setId(1L);
        patient.setLastname("Pomme");
        patient.setFirstname("Abricot");
        patient.setActive(true);
    }

    @Test
    public void testGetPatients() {
        // Arrange
        List<Patient> patients = Arrays.asList(patient);
        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getPatients();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Abricot", result.get(0).getFirstname());
        assertEquals("Pomme", result.get(0).getLastname());
    }

    @Test
    public void testGetPatientById_PatientExists() {
        // Arrange
        Long id = 1L;
        Patient patient = new Patient();
        patient.setId(id);
        patient.setActive(true);
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientService.getPatientById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(patient, result.get());
        verify(patientRepository, times(1)).findById(id);
    }

//    @Test
//    public void testGetPatientById_NotFound() {
//        // Arrange
//        Long id = 1L;
//        Patient patient = new Patient();
//        patient.setId(id);
//        patient.setActive(false);
//        when(patientRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Act
//        Optional<Patient> result = patientService.getPatientById(id);
//
//        // Assert
//        assertTrue(result.isEmpty());
//        verify(patientRepository, times(1)).findById(id);
//    }

    @Test
    public void testAddPatient() {
        // Arrange
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Act
        Patient result = patientService.savePatient(new Patient());

        // Assert
        assertNotNull(result);
        assertEquals("Abricot", result.getFirstname());
    }

    @Test
    public void testUpdatePatient_Success() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        when(patientRepository.existsById(patientId)).thenReturn(true);
        when(patientRepository.save(patient)).thenReturn(patient);

        // Act
        Patient updatedPatient = patientService.updatePatient(patient);

        // Assert
        assertEquals(patient, updatedPatient);
        verify(patientRepository).save(patient);
    }

    @Test
    public void testUpdatePatient_PatientNotFound() {
        // Arrange & Act
        Long patientId = 2L;
        Patient patient = new Patient();
        patient.setId(patientId);
        when(patientRepository.existsById(patientId)).thenReturn(false);

        // assert
        assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(patient));
        verify(patientRepository, never()).save(any(Patient.class));
    }



//    @Test
//    public void testDeletePatientById_Success_WithNotes() {
//        // Arrange
//        Long patientId = 1L;
//        when(patientRepository.existsById(patientId)).thenReturn(true);
//        when(feignClient.existsNotesByPatientId(patientId)).thenReturn(true);
//
//        // Act
//        boolean result = patientService.deletePatientById(patientId);
//
//        // Assert
//        assertTrue(result);
//        verify(feignClient, times(1)).deleteNotesByPatientId(patientId);
//        verify(patientRepository, times(1)).deleteById(patientId);
//    }
//
//    @Test
//    public void testDeletePatientById_Success_NoNotes() {
//        // Arrange
//        Long patientId = 1L;
//        when(patientRepository.existsById(patientId)).thenReturn(true);
//        when(feignClient.existsNotesByPatientId(patientId)).thenReturn(false);
//
//        // Act
//        boolean result = patientService.deletePatientById(patientId);
//
//        // Assert
//        assertTrue(result);
//        verify(feignClient, never()).deleteNotesByPatientId(patientId);
//        verify(patientRepository, times(1)).deleteById(patientId);
//    }
//
//    @Test
//    public void testDeletePatientById_Failure_NotesCheckFails() {
//        // Arrange
//        Long patientId = 1L;
//        when(patientRepository.existsById(patientId)).thenReturn(true);
//        doThrow(new RuntimeException("Feign client error")).when(feignClient).existsNotesByPatientId(patientId);
//
//        // Act
//        boolean result = patientService.deletePatientById(patientId);
//
//        // Assert
//        assertFalse(result);
//        verify(patientRepository, never()).deleteById(patientId);
//        verify(feignClient, times(1)).existsNotesByPatientId(patientId);
//    }
//
//    @Test
//    public void testDeletePatientById_Failure_UnexpectedError() {
//        // Arrange
//        Long patientId = 3L;
//        when(patientRepository.existsById(patientId)).thenReturn(true);
//        doThrow(new RuntimeException("Database error")).when(patientRepository).deleteById(patientId);
//
//        // Act
//        boolean result = patientService.deletePatientById(patientId);
//
//        // Assert
//        assertFalse(result);
//        verify(feignClient, times(1)).existsNotesByPatientId(patientId);
//        verify(patientRepository, times(1)).deleteById(patientId);
//    }

    @Test
    public void testDeactivatePatientById_Success() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setActive(true);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);

        // Act
        boolean result = patientService.deactivatePatientById(patientId);

        // Assert
        assertTrue(result);
        assertFalse(patient.isActive());
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void testDeactivatePatientById_PatientNotFound() {
        // Arrange
        Long patientId = 2L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act
        boolean result = patientService.deactivatePatientById(patientId);

        // Assert
        assertFalse(result);
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    public void testDeactivatePatientById_ExceptionThrown() {
        // Arrange
        Long patientId = 3L;
        when(patientRepository.findById(patientId)).thenThrow(new RuntimeException("Database error"));

        // Act
        boolean result = patientService.deactivatePatientById(patientId);

        // Assert
        assertFalse(result);
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, never()).save(any(Patient.class));
    }

}