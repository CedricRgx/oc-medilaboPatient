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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    private Patient patient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient();
        patient.setId(1L);
        patient.setLastname("Pomme");
        patient.setFirstname("Abricot");
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
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientService.getPatientById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(patient, result.get());
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPatientById_NotFound() {
        // Arrange
        Long id = 1L;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Patient> result = patientService.getPatientById(id);

        // Assert
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).findById(id);
    }

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
    public void testUpdatePatient() {
        // Arrange
        when(patientRepository.existsById(patient.getId())).thenReturn(true);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Act
        Patient result = patientService.updatePatient(patient);

        // Assert
        assertNotNull(result);
        assertEquals("Abricot", result.getFirstname());
    }

    @Test
    public void testDeletePatientById() {
        // Arrange
        Long id = 1L;
        when(patientRepository.existsById(id)).thenReturn(true);
        doNothing().when(patientRepository).deleteById(id);

        // Act
        boolean result = patientService.deletePatientById(id);

        // Assert
        assertTrue(result);
        verify(patientRepository, times(1)).deleteById(id);
    }

}