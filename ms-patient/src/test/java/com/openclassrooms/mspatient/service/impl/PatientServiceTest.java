package com.openclassrooms.mspatient.service.impl;

import com.openclassrooms.mspatient.exceptions.PatientNotFoundException;
import com.openclassrooms.mspatient.model.Patient;
import com.openclassrooms.mspatient.repository.PatientRepository;
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
        List<Patient> patients = Arrays.asList(patient);
        when(patientRepository.findAll()).thenReturn(patients);
        List<Patient> result = patientService.getPatients();
        assertEquals(1, result.size());
        assertEquals("Abricot", result.get(0).getFirstname());
        assertEquals("Pomme", result.get(0).getLastname());
    }

    @Test
    public void testGetPatientById_PatientExists() {
        Long id = 1L;
        Patient patient = new Patient();
        patient.setId(id);
        when(patientRepository.existsById(id)).thenReturn(true);
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        Optional<Patient> result = patientService.getPatientById(id);

        assertTrue(result.isPresent());
        assertEquals(patient, result.get());
        verify(patientRepository, times(1)).existsById(id);
        verify(patientRepository, times(1)).findById(id);
    }


    @Test
    public void testGetPatientById_NotFound() {
        when(patientRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(PatientNotFoundException.class, () -> {
            patientService.getPatientById(1L);
        });

        String expectedMessage = "Patient not found with ID: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAddPatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        Patient result = patientService.addPatient(new Patient());
        assertNotNull(result);
        assertEquals("Abricot", result.getFirstname());
    }

    @Test
    public void testUpdatePatient() {
        when(patientRepository.existsById(patient.getId())).thenReturn(true);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        Patient result = patientService.updatePatient(patient);
        assertNotNull(result);
        assertEquals("Abricot", result.getFirstname());
    }

    @Test
    public void testDeletePatientById() {
        Long id = 1L;
        when(patientRepository.existsById(id)).thenReturn(true);
        doNothing().when(patientRepository).deleteById(id);
        boolean result = patientService.deletePatientById(id);
        assertTrue(result);
        verify(patientRepository, times(1)).deleteById(id);
    }

}