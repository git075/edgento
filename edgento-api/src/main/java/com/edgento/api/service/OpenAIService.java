/**
 * WHAT: Service to communicate with OpenAI APIs.
 * WHY: Centralizes LLM integrations so the main agent service isn't tied to a specific provider's HTTP client structure.
 */
package com.edgento.api.service;

import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    public String generateReply(String systemPrompt, String userMessage) {
        // TODO: implement logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
