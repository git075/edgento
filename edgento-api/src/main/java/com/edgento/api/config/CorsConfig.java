/**
 * WHAT: CORS Configuration for the application.
 * WHY: Needed to allow frontend applications (like React/Next.js) to communicate with this backend across different origins.
 * HOW: Implements WebMvcConfigurer to customize Spring MVC configuration.
 */
package com.edgento.api.config;

// 📚 CONCEPT: @Configuration - Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Marks this class as a source of bean definitions
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures cross origin requests processing.
     * @param registry Assists with the registration of CorsConfiguration mapped to a path pattern.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // // Why not X? We don't want to allow all origins in production, but for scaffolding we open it up. TODO: restrict in prod.
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
