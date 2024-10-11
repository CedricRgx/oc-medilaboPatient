package com.openclassrooms.msclientui.proxies;

import com.openclassrooms.msclientui.beans.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="ms-patient", url="${patient.service.url}")
public interface PatientFeignClient {

    @GetMapping("/allpatients")
    List<Patient> getPatientsList();

    @GetMapping("/${id}")
    Patient getPatientById(@PathVariable("id") Long id);

    @PostMapping()
    Patient savePatient(Patient patient);

    @PutMapping("/${id}")
    Patient updatePatient(@PathVariable("id") Long id, Patient patient);

    @DeleteMapping("/${id}")
    boolean deletePatient(@PathVariable("id") Long id);

}
