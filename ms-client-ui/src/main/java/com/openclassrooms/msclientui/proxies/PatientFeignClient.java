package com.openclassrooms.msclientui.proxies;

import com.openclassrooms.msclientui.beans.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="ms-patient", url="http://localhost:8081/patient")
public interface PatientFeignClient {

    @GetMapping("/allpatients")
    List<Patient> getPatientsList();

    @GetMapping("/{id}")
    Patient getPatientById(@PathVariable("id") Long id);

    @PostMapping()
    Patient savePatient(@RequestBody Patient patient);

    @PutMapping("")
    Patient updatePatient(@RequestBody Patient patient);

    @DeleteMapping("/{id}")
    boolean deletePatient(@PathVariable("id") Long id);

}
