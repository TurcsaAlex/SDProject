package com.example.project1.services.Observer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("belextest1@gmail.com");
        message.setTo(to);
        message.setSubject("Supermarket invoice");
        message.setText(body);

        mailSender.send(message);
    }
}
