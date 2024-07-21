package org.tedygabrielmoisa.authenticationserver.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.tedygabrielmoisa.authenticationserver.authentication.filters.JwtAuthenticationFilter;
import org.tedygabrielmoisa.authenticationserver.authentication.filters.JwtRefreshAuthenticationFilter;
import org.tedygabrielmoisa.authenticationserver.enums.Role;

/**
 * Configuration class for setting up the security filters and authorization rules.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtRefreshAuthenticationFilter jwtRefreshAuthenticationFilter;

    /**
     * Configures the security filter chain.
     *
     * @param http the {@link HttpSecurity} object to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs while setting up the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtRefreshAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(c -> c
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/pokemons/create").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                .requestMatchers("/pokemons").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name(), Role.USER.name())
                .anyRequest().hasAuthority(Role.ADMIN.name()));

        http.exceptionHandling(e -> e.authenticationEntryPoint((request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
        );

        return http.build();
    }
}
