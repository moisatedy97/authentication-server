package org.tedygabrielmoisa.authenticationserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling email operations.
 * Utilizes {@link JavaMailSender} to send emails.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Sends an email to a specified recipient with a given subject and body.
     *
     * @param to      the email address of the recipient
     * @param subject the subject line of the email
     * @param body    the main content of the email
     */
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
