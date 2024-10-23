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

/**
 * Configuration class for Spring Security
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * Configures the SecurityFilterChain to specify security settings
     *
     * @param http HttpSecurity used to configure the security filter chain.
     * @return the configured SecurityFilterChain object.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    /**
     * Defines the bean for password encoding using BCrypt hashing algorithm.
     *
     * @return a BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines the bean for a UserDetailsService bean that provides an user
     * details service
     *
     * @param passwordEncoder the PasswordEncoder used to encode the user's password
     * @return a UserDetailsService containing the user details for authentication
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("username")
                .password(passwordEncoder.encode("password"))
                .build();
        return new InMemoryUserDetailsManager(user);
    }


}
