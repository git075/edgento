/**
 * WHAT: Main application entry point for Edgento API.
 * WHY: Bootstraps the Spring Boot application context.
 * HOW: Contains the main method that delegates to SpringApplication.run.
 */
package com.edgento.api;

// 📚 CONCEPT: SpringBootApplication - This is a convenience annotation that adds:
// @Configuration: Tags the class as a source of bean definitions.
// @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings.
// @ComponentScan: Tells Spring to look for other components, configurations, and services in the com.edgento.api package.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Configures this class as a Spring Boot application
public class EdgentoApiApplication {

    /**
     * Entry point of the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // Starts the application context
        SpringApplication.run(EdgentoApiApplication.class, args);
    }
}
