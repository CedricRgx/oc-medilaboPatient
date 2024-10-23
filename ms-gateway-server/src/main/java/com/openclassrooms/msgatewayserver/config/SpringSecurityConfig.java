package com.openclassrooms.msgatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configuration class for Spring Security
 */
@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    /**
     * Configures the SecurityFilterChain to specify security settings
     *
     * @param http HttpSecurity used to configure the security filter chain.
     * @return the configured SecurityFilterChain object.
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                .build();
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
     * Defines the bean for a MapReactiveUserDetailsService bean that provides an in-memory user
     * details service
     *
     * @param passwordEncoder the PasswordEncoder used to encode the user's password
     * @return a MapReactiveUserDetailsService containing the user details for authentication
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("username")
                .password(passwordEncoder.encode("password"))
                .build();
        return new MapReactiveUserDetailsService(user);
    }
}
