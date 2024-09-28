package com.openclassrooms.patientmanagement.service.impl;

import com.openclassrooms.patientmanagement.model.Patient;
import com.openclassrooms.patientmanagement.repository.PatientRepository;
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
        patient.setIdPat(1L);
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
    public void testGetPatientById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientService.getPatientById(1L);

        assertTrue(result.isPresent());
        assertEquals("Abricot", result.get().getFirstname());
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
        when(patientRepository.existsById(patient.getIdPat())).thenReturn(true);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient result = patientService.updatePatient(patient);

        assertNotNull(result);
        assertEquals("Abricot", result.getFirstname());
    }

    @Test
    public void testDeletePatientById() {
        doNothing().when(patientRepository).deleteById(1L);

        boolean result = patientService.deletePatientById(1L);

        assertTrue(result);
        verify(patientRepository, times(1)).deleteById(1L);
    }
}