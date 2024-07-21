package org.tedygabrielmoisa.authenticationserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to set up Cross-Origin Resource Sharing (CORS) settings.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${spring.application.origin}")
    private String origin;

    /**
     * Configures the CORS mappings.
     *
     * @param registry the {@link CorsRegistry} to add mappings to
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(origin)
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
