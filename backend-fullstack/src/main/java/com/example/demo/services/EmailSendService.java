package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserMessage;

@Service
public class EmailSendService {

    // @Bean
    // public void SendMail(UserMessage messageBody) {

    // }

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String username, String email, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(username);
        // message.setText();
        message.setText("Senders Mail ID : " + email + "                                " + "Message : " + text);
        javaMailSender.send(message);
    }

    public void verifyMail(String To, String Otp) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(To);
        simpleMailMessage.setSubject("Verification Otp");
        simpleMailMessage.setText(Otp);

        javaMailSender.send(simpleMailMessage);

    }

}
