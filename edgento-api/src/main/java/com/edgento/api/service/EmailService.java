package com.edgento.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.List;

@Slf4j
@Service
public class EmailService {

    private final RestClient restClient;
    private final String resendApiKey;

    public EmailService(@Value("${resend.api-key}") String resendApiKey) {
        this.resendApiKey = resendApiKey;
        this.restClient = RestClient.create();
    }

    public void sendEmail(String to, String subject, String body) {
        log.info("Attempting to send email to {} via Resend API", to);
        
        try {
            Map<String, Object> payload = Map.of(
                    "from", "Edgento <onboarding@resend.dev>",
                    "to", List.of(to),
                    "subject", subject,
                    "html", body
            );

            restClient.post()
                    .uri("https://api.resend.com/emails")
                    .header("Authorization", "Bearer " + resendApiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();
                    
            log.info("Successfully sent email to {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}. Error: {}", to, e.getMessage());
            // We swallow the exception here so that if the email fails (e.g. invalid API key),
            // it doesn't crash the user's contact form submission. The DB save still succeeds.
        }
    }
}
