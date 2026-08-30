/*
 * WHAT: Core Spring Security Configuration.
 * WHY:  To protect sensitive endpoints (like /leads) from public access.
 *       By default, Spring Security locks down EVERYTHING. We explicitly open up
 *       the endpoints that the public frontend needs to function.
 * HOW:  Uses SecurityFilterChain to define which HTTP paths require authentication
 *       and which are public (permitAll). Also disables CSRF because this is a stateless
 *       REST API, not a server-rendered form app.
 */
package com.edgento.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // Integrate with our global CORS configuration (CorsConfig.java)
            // Without this, Spring Security intercepts and blocks CORS preflight OPTIONS requests.
            .cors(org.springframework.security.config.Customizer.withDefaults())
            
            // 📚 CONCEPT: CSRF (Cross-Site Request Forgery)
            // CSRF protection is vital for apps that use cookies for authentication.
            // Since our API currently has no user authentication (and will use stateless JWTs later),
            // we disable CSRF so the React frontend can successfully send POST requests.
            .csrf(AbstractHttpConfigurer::disable)
            
            // Define rules for HTTP requests
            .authorizeHttpRequests(auth -> auth
                // Allow anyone to start an audit and send messages
                .requestMatchers(HttpMethod.POST, "/api/v1/agent/**").permitAll()
                
                // Allow anyone to submit a lead
                .requestMatchers(HttpMethod.POST, "/api/v1/leads").permitAll()
                
                // Allow anyone to submit the contact form
                .requestMatchers(HttpMethod.POST, "/api/v1/contact").permitAll()
                
                // (Future) The Admin Panel endpoints will go here and require ROLE_ADMIN
                
                // ANY other request (like GET /leads) must be authenticated.
                // Right now, this acts as a hard lockdown since we haven't built login.
                .anyRequest().authenticated()
            )
            .build();
    }
}
