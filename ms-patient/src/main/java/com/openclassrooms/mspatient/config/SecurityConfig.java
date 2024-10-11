/*
package com.openclassrooms.mspatient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

*/
/**
 * Configuration class for Spring Security, enabling and customizing web security for the MedilaboPatient application.
 *//*

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    */
/**
     * Configures the SecurityFilterChain to specify security settings.
     * @param http HttpSecurity used to configure the security filter chain.
     * @return the configured SecurityFilterChain object.
     * @throws Exception if an error occurs during configuration.
     *//*

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/**")
                .hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    */
/**
     * Bean for PasswordEncoder to use for encoding passwords.
     * @return a BCryptPasswordEncoder instance.
     *//*

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    */
/**
     * Creates and configures an in-memory user details service with hardcoded users for authentication.
     * @return An InMemoryUserDetailsManager containing the details of the created users.
     *//*

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = passwordEncoder();

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}*/
