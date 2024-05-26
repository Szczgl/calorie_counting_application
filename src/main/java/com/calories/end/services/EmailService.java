package com.calories.end.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static EmailService instance;

    private JavaMailSender javaMailSender;

    private EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public static synchronized EmailService getInstance(JavaMailSender javaMailSender) {
        if (instance == null) {
            instance = new EmailService(javaMailSender);
        }
        return instance;
    }

    public void sendCalorieReport(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
