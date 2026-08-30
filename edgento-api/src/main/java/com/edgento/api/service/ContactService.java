package com.edgento.api.service;

import com.edgento.api.model.dto.request.ContactRequest;
import com.edgento.api.model.entity.ContactMessage;
import com.edgento.api.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactMessageRepository contactMessageRepository;
    private final EmailService emailService;

    // Hardcoded for now based on implementation plan open question.
    private static final String ADMIN_EMAIL = "admin@edgento.com";

    public void processContactSubmission(ContactRequest request) {
        log.info("Processing contact submission from: {}", request.getEmail());

        // 1. Save to Database
        ContactMessage message = ContactMessage.builder()
                .name(request.getName())
                .email(request.getEmail())
                .message(request.getMessage())
                .build();
        
        contactMessageRepository.save(message);

        // 2. Send notification email to Admin
        String adminSubject = "New Contact Form Submission from " + request.getName();
        String adminBody = String.format(
                "<p><strong>Name:</strong> %s</p><p><strong>Email:</strong> %s</p><p><strong>Message:</strong></p><p>%s</p>",
                request.getName(), request.getEmail(), request.getMessage()
        );
        emailService.sendEmail(ADMIN_EMAIL, adminSubject, adminBody);

        // 3. Send Auto-Reply to User
        String userSubject = "We received your message!";
        String userBody = String.format(
                "<p>Hi %s,</p><p>Thank you for reaching out to Edgento. We have received your message and our team will get back to you within 24 hours.</p><br/><p>Best,<br/>The Edgento Team</p>",
                request.getName()
        );
        emailService.sendEmail(request.getEmail(), userSubject, userBody);
    }
}
