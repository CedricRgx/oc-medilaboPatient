package com.openclassrooms.msclientui.proxy.config;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Configuration class for Feign clients that provides a basic authentication interceptor.
 */
@Configuration
public class FeignClientConfig {

    /**
     * The username for the Feign client
     */
    @Value("${feign.client.username}")
    private String username;

    /**
     * The password for the Feign client
     */
    @Value("${feign.client.password}")
    private String password;

    /**
     * Creates a RequestInterceptor bean that adds the Basic Authentication header to every request.
     *
     * @return a RequestInterceptor that sets the Authorization header with Basic Authentication
     */
//    @Bean
//    public RequestInterceptor basicAuthRequestInterceptor() {
//        return requestTemplate -> {
//            String auth = "username:password";
//            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
//            String authHeader = "Basic " + encodedAuth;
//            requestTemplate.header("Authorization", authHeader);
//        };
//    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }
    //TODO créer des validateurs (voir birthdate sur PayMyBuddy) sur l'adresse et le numéro de téléphone (ils sont optionnels mais réponde à un certain format)

}