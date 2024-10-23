package com.openclassrooms.msclientui.proxy.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Configuration class for Feign clients that provides a basic authentication interceptor.
 */
@Configuration
public class PatientFeignClientConfig {

    /**
     * Creates a RequestInterceptor bean that adds the Basic Authentication header to every request.
     *
     * @return a RequestInterceptor that sets the Authorization header with Basic Authentication
     */
    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return requestTemplate -> {
            String auth = "username:password";
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + encodedAuth;
            requestTemplate.header("Authorization", authHeader);
        };
    }

}