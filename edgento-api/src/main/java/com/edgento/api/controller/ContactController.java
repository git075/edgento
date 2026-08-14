/*
 * WHAT: REST controller for handling contact form submissions.
 * WHY:  When a user fills out the 'Contact Us' form on the frontend,
 *       it sends a POST request here to be processed (e.g., saved to DB,
 *       email notification sent).
 * HOW:  Parses the incoming JSON into a ContactRequest DTO, validates it,
 *       and currently returns a hardcoded success message.
 */
package com.edgento.api.controller;

import com.edgento.api.model.dto.request.ContactRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {

    // POST /api/v1/contact
    // Accepts the contact form submission.
    @PostMapping
    public ResponseEntity<Map<String, String>> submitContact(@Valid @RequestBody ContactRequest request) {
        // TODO: Phase B - Save contact message to DB and send email notification
        
        // Return a simple JSON object: { "message": "Thank you!..." }
        return ResponseEntity.ok(Map.of(
                "message", "Thank you! We'll get back to you within 24 hours."
        ));
    }
}
