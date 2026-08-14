/**
 * WHAT: Service to handle outgoing emails.
 * WHY: Abstract email sending logic for notifications.
 */
package com.edgento.api.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(String to, String subject, String body) {
        // TODO: implement logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
