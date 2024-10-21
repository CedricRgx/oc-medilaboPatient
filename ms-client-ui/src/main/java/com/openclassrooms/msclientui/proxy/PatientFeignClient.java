package com.openclassrooms.msclientui.proxy;

import com.openclassrooms.msclientui.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name="ms-patient", url="http://localhost:8081")
@FeignClient(name="ms-gateway-server", url="http://localhost:8082")
public interface PatientFeignClient {

    @GetMapping("/patient/allpatients")
    List<Patient> getPatientsList();

    @GetMapping("/patient/{id}")
    Patient getPatientById(@PathVariable("id") Long id);

    @PostMapping("/patient")
    Patient savePatient(@RequestBody Patient patient);

    @PutMapping("/patient")
    Patient updatePatient(@RequestBody Patient patient);

    @DeleteMapping("/patient/{id}")
    boolean deletePatient(@PathVariable("id") Long id);

}
