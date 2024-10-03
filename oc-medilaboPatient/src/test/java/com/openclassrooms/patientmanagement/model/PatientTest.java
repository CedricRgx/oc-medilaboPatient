package com.openclassrooms.patientmanagement.model;

import com.openclassrooms.patientmanagement.util.Gender;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;

public class PatientTest {

    @Test
    public void testGetId() {
        Patient patient = new Patient();
        patient.setId(1L);
        assertThat(patient.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetLastname() {
        Patient patient = new Patient();
        patient.setLastname("Dupont");
        assertThat(patient.getLastname()).isEqualTo("Dupont");
    }

    @Test
    public void testGetFirstname() {
        Patient patient = new Patient();
        patient.setFirstname("Charles");
        assertThat(patient.getFirstname()).isEqualTo("Charles");
    }

    @Test
    public void testGetBirthdate() {
        LocalDate birthdate = LocalDate.of(1998, 2, 3);
        Patient patient = new Patient();
        patient.setBirthdate(birthdate);
        assertThat(patient.getBirthdate()).isEqualTo(birthdate);
    }

    @Test
    public void testGetGender() {
        Patient patient = new Patient();
        patient.setGender(Gender.M);
        assertThat(patient.getGender()).isEqualTo(Gender.M);
    }

    @Test
    public void testGetAddress() {
        Patient patient = new Patient();
        patient.setAddress("45 rue de la Rue, 65000 Ville");
        assertThat(patient.getAddress()).isEqualTo("45 rue de la Rue, 65000 Ville");
    }

    @Test
    public void testGetPhone() {
        Patient patient = new Patient();
        patient.setPhone("123-456-7890");
        assertThat(patient.getPhone()).isEqualTo("123-456-7890");
    }

    // Tests pour les setters

    @Test
    public void testSetId() {
        Patient patient = new Patient();
        patient.setId(2L);
        assertThat(patient.getId()).isEqualTo(2L);
    }

    @Test
    public void testSetLastname() {
        Patient patient = new Patient();
        patient.setLastname("Anjou");
        assertThat(patient.getLastname()).isEqualTo("Anjou");
    }

    @Test
    public void testSetFirstname() {
        Patient patient = new Patient();
        patient.setFirstname("Marguerite");
        assertThat(patient.getFirstname()).isEqualTo("Marguerite");
    }

    @Test
    public void testSetBirthdate() {
        LocalDate birthdate = LocalDate.of(1993, 10, 23);
        Patient patient = new Patient();
        patient.setBirthdate(birthdate);
        assertThat(patient.getBirthdate()).isEqualTo(birthdate);
    }

    @Test
    public void testSetGender() {
        Patient patient = new Patient();
        patient.setGender(Gender.F);
        assertThat(patient.getGender()).isEqualTo(Gender.F);
    }

    @Test
    public void testSetAddress() {
        Patient patient = new Patient();
        patient.setAddress("76 boulevard du Boulevard, 65003 Grande ville");
        assertThat(patient.getAddress()).isEqualTo("76 boulevard du Boulevard, 65003 Grande ville");
    }

    @Test
    public void testSetPhone() {
        Patient patient = new Patient();
        patient.setPhone("987-654-3210");
        assertThat(patient.getPhone()).isEqualTo("987-654-3210");
    }
}