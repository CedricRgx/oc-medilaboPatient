package com.openclassrooms.msclientui.proxies;

import com.openclassrooms.msclientui.beans.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="ms-patient", url="${patient.service.url}")
public interface PatientFeignClient {

    @GetMapping("/allpatients")
    List<Patient> getPatientsList();

    @GetMapping("/${id}")
    Patient getPatientById(@PathVariable("id") Long id);

}
