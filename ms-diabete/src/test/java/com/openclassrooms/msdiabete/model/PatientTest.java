package com.openclassrooms.msdiabete.model;

import com.openclassrooms.msdiabete.util.Gender;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;

public class PatientTest {

    @Test
    public void testGetId() {
        // Arrange & Act
        Patient patient = new Patient();
        patient.setId(1L);

        // Assert
        assertThat(patient.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetLastname() {
        // Arrange & Act
        Patient patient = new Patient();
        patient.setLastname("Dupont");

        // Assert
        assertThat(patient.getLastname()).isEqualTo("Dupont");
    }

    @Test
    public void testGetFirstname() {
        // Arrange & Act
        Patient patient = new Patient();
        patient.setFirstname("Charles");

        // Assert
        assertThat(patient.getFirstname()).isEqualTo("Charles");
    }

    @Test
    public void testGetBirthdate() {
        // Arrange & Act
        LocalDate birthdate = LocalDate.of(1998, 2, 3);
        Patient patient = new Patient();
        patient.setBirthdate(birthdate);

        // Assert
        assertThat(patient.getBirthdate()).isEqualTo(birthdate);
    }

    @Test
    public void testGetGender() {
        // Arrange & Act
        Patient patient = new Patient();
        patient.setGender(Gender.M);

        // Assert
        assertThat(patient.getGender()).isEqualTo(Gender.M);
    }


    @Test
    public void testSetId() {
        // Arrange & Act
        Patient patient = new Patient();
        patient.setId(2L);

        // Assert
        assertThat(patient.getId()).isEqualTo(2L);
    }

    @Test
    public void testSetLastname() {
        // Arrange & Act
        Patient patient = new Patient();
        patient.setLastname("Anjou");
        assertThat(patient.getLastname()).isEqualTo("Anjou");
    }

    @Test
    public void testSetFirstname() {
        // Arrange & Act
        Patient patient = new Patient();
        patient.setFirstname("Marguerite");

        // Assert
        assertThat(patient.getFirstname()).isEqualTo("Marguerite");
    }

    @Test
    public void testSetBirthdate() {
        // Arrange & Act
        LocalDate birthdate = LocalDate.of(1993, 10, 23);
        Patient patient = new Patient();
        patient.setBirthdate(birthdate);

        // Assert
        assertThat(patient.getBirthdate()).isEqualTo(birthdate);
    }

    @Test
    public void testSetGender() {
        // Arrange & Act
        Patient patient = new Patient();
        patient.setGender(Gender.F);

        // Assert
        assertThat(patient.getGender()).isEqualTo(Gender.F);
    }

}