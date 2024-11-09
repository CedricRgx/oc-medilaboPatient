package com.openclassrooms.msnote.proxy;

import com.openclassrooms.msnote.proxy.config.FeignClientConfig;
import org.springframework.web.bind.annotation.*;

/**
 * Feign client interface to interact with the Patient microservice through the ms-gateway-server
 */
@org.springframework.cloud.openfeign.FeignClient(name="ms-gateway-server", url="http://localhost:8082", configuration= FeignClientConfig.class)
public interface FeignClient {

    /**
     * Checks if a patient exists via their ID.
     *
     * @param id the ID of the patient to check
     * @return a Boolean, true if the patient exists, false otherwise.
     */
    @GetMapping("/patient/exist/{id}")
    Boolean isExist(@PathVariable("id") Long id);




}
