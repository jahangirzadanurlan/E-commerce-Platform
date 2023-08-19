package com.example.productservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    public MailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendProductUpdateMail(String email, String productName) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(email);
            helper.setSubject("Product Status Update!");

            String emailContent = "<html><body>"
                    + "<p>The sell status of product '" + productName + "' has been updated to 'available for sale'.</p>"
                    + "</body></html>";

            helper.setText(emailContent, true);

            javaMailSender.send(message);
            log.info("Product update email sent: " + email);
        } catch (Exception e) {
            log.error("Error while sending product update email", e); // Log the error
        }
    }
}
