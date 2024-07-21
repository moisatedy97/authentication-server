package org.tedygabrielmoisa.authenticationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tedygabrielmoisa.authenticationserver.authentication.providers.OtpAuthenticationProvider;
import org.tedygabrielmoisa.authenticationserver.authentication.providers.UsernamePasswordAuthenticationProvider;

/**
 * Configuration class for setting up authentication components.
 */
@Configuration
public class AuthenticationConfig {

    /**
     * Configures the {@link AuthenticationManager} with multiple authentication providers.
     *
     * @param http                            the {@link HttpSecurity} object
     * @param usernamePasswordAuthenticationProvider the provider for username and password authentication
     * @param otpAuthenticationProvider       the provider for OTP authentication
     * @param daoAuthenticationProvider       the DAO authentication provider
     * @return the configured {@link AuthenticationManager}
     * @throws Exception if an error occurs while configuring the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider,
            OtpAuthenticationProvider otpAuthenticationProvider,
            DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.authenticationProvider(otpAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(usernamePasswordAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider);

        return authenticationManagerBuilder.build();
    }

    /**
     * Provides a {@link PasswordEncoder} bean that uses BCrypt hashing algorithm.
     *
     * @return a {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
