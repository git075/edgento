/*
 * WHAT: Global CORS configuration.
 * WHY:  Browsers block JavaScript from calling APIs on a different domain/port by default.
 *       Our React app runs on localhost:5173 but our API runs on localhost:8080.
 *       This is a different port, so the browser considers it a "different origin" and blocks it.
 *       This config tells Spring to allow requests from our frontend origins.
 *
 * 📚 CONCEPT: CORS (Cross-Origin Resource Sharing)
 * When a webpage at domain-A tries to call an API at domain-B, the browser
 * first sends a "preflight" OPTIONS request to check if domain-B allows it.
 * If domain-B responds with the right CORS headers, the browser allows the call.
 * If not, the browser blocks it — even if the API response arrived fine.
 */
package com.edgento.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origins}")
    private List<String> allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 📚 CONCEPT: Dynamic Configuration
        // Instead of hardcoding localhost here, we inject a list of origins from application.yml.
        // In dev, it's localhost. In prod, it's edgento.com.
        config.setAllowedOrigins(allowedOrigins);

        // Allow these HTTP methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow these request headers
        config.setAllowedHeaders(List.of("*"));

        // Allow cookies and Authorization headers to be sent
        config.setAllowCredentials(true);

        // How long the browser can cache the preflight response (1 hour)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
