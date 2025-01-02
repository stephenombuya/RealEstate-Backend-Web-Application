package com.realestate.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com");  // Replace with your email address
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }
}
