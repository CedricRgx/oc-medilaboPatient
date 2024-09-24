package com.openclassrooms.patientmanagement.repository;

import com.openclassrooms.patientmanagement.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Patient entities.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
