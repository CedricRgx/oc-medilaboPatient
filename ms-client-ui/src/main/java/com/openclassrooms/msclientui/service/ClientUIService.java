package com.openclassrooms.msclientui.service;

import com.openclassrooms.msclientui.beans.Patient;
import com.openclassrooms.msclientui.exception.PatientNotFoundException;
import com.openclassrooms.msclientui.proxies.PatientFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class ClientUIService {

    private final PatientFeignClient patientFeignClient;

    public ClientUIService(PatientFeignClient patientFeignClient) {
        this.patientFeignClient = patientFeignClient;
    }

    public List<Patient> getPatientsList() {
        return patientFeignClient.getPatientsList();
    }

    public Patient getPatientById(Long id) {
        Patient patient = patientFeignClient.getPatientById(id);
        if (patient == null) {
            throw new PatientNotFoundException("Patient not found with ID: " + id);
        }
        return patient;
    }

    public Patient savePatient(Patient patient){
        return patientFeignClient.savePatient(patient);
    }

    public Patient updatePatient(Long id, Patient patient){
        return patientFeignClient.updatePatient(patient);
    }

    public boolean deletePatient(Long id){
        boolean isDeleted = patientFeignClient.deletePatient(id);
        return isDeleted;
    }

}
