package com.openclassrooms.msclientui.service;

import com.openclassrooms.msclientui.model.Patient;
import com.openclassrooms.msclientui.exception.PatientNotFoundException;
import com.openclassrooms.msclientui.proxies.PatientFeignClient;
import com.openclassrooms.msclientui.util.CustomPage;
import org.springframework.stereotype.Service;

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

    public CustomPage<Patient> getPatientsList(int page, int size) {
        List<Patient> patientslist = patientFeignClient.getPatientsList();

        int totalPatients = patientslist.size();
        int totalPages = (int) Math.ceil((double) totalPatients / size);
        int start = page * size;
        int end = Math.min((start + size), totalPatients);

        List<Patient> pageContent = patientslist.subList(start, end);
        return new CustomPage<>(pageContent, totalPages, page);
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
