/*
 * WHAT: Spring @Configuration class that creates and configures the OpenAI WebClient bean.
 * WHY:  We need a pre-configured HTTP client to call OpenAI's API. By creating it as a
 *       Spring @Bean, we only build it once and inject it wherever needed.
 * HOW:  WebClient is Spring WebFlux's reactive HTTP client. It supports streaming responses
 *       (Flux<String>) which is exactly what we need for SSE token streaming.
 *
 * 📚 CONCEPT: Spring @Bean
 * A @Bean method inside a @Configuration class tells Spring: "Call this method once,
 * store the result in the application context, and give it to anyone who asks for it
 * via @Autowired or constructor injection."
 *
 * 📚 CONCEPT: WebClient vs RestTemplate
 * RestTemplate is the old Spring HTTP client — it's synchronous (blocks the thread until
 * the response arrives). WebClient is reactive — it can handle thousands of concurrent
 * requests without blocking threads. We NEED WebClient here because streaming SSE
 * from OpenAI requires a non-blocking client.
 */
package com.edgento.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OpenAIConfig {

    // @Value injects the value of 'openai.api-key' from application.yml (or environment variable).
    // The ${...} syntax is Spring Expression Language (SpEL) for reading config values.
    @Value("${openai.api-key}")
    private String openAiApiKey;

    @Value("${openai.model:gpt-4o-mini}")
    private String model;

    // This method creates and returns a WebClient configured specifically for OpenAI.
    // Spring will call this once at startup and cache the result.
    @Bean
    public WebClient openAiWebClient() {
        return WebClient.builder()
                // Base URL: all requests from this client start with this prefix
                .baseUrl("https://api.openai.com/v1")
                // Default header: every request automatically includes the Authorization header
                // OpenAI requires: "Authorization: Bearer sk-..."
                .defaultHeader("Authorization", "Bearer " + openAiApiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    // 📚 CONCEPT: RestClient
    // Introduced in Spring 6.1, RestClient is a modern, fluent, synchronous HTTP client.
    // Unlike WebClient, calling .body(...) on RestClient doesn't require .block() because
    // it executes synchronously on the calling thread by design. We use this for our
    // blocking OpenAI calls to avoid exhausting the WebFlux thread pool.
    @Bean
    public org.springframework.web.client.RestClient openAiRestClient() {
        return org.springframework.web.client.RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + openAiApiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    // Expose model name as a bean property so OpenAIService can use it
    @Bean
    public String openAiModel() {
        return model;
    }
}
