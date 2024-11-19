package com.example.iot.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailNotificationService {
    JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail); // Email nhận
    message.setSubject(subject);      // Tiêu đề email
    message.setText(body); // Nội dung email
    message.setFrom("namtran10a2nt@gmail.com");  // Email người gửi

    mailSender.send(message);
    System.out.println("Email sent successfully!");
}
}
