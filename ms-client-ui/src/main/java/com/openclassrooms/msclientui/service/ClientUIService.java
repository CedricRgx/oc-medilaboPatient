package com.openclassrooms.msclientui.service;

import com.openclassrooms.msclientui.beans.Patient;
import com.openclassrooms.msclientui.proxies.PatientFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUIService {

    @Autowired
    private PatientFeignClient patientFeignClient;

    public List<Patient> getPatientsList() {
        return patientFeignClient.getPatientsList();
    }

    public Patient getPatientById(Long id) {
        return patientFeignClient.getPatientById(id);
    }

}
