package com.example.demo.services;

import java.util.Random;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    @Autowired
    private JavaMailSender mailSender;

    public String SendResetOtp(String email) {

        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        String numbers = "1234567890";
        StringBuilder otp = new StringBuilder(6);
        Random random = new Random();
        simpleMessage.setTo(email);
        for (int i = 0; i < 6; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        String otpFinal = otp.toString();
        simpleMessage.setSubject("Reset Otp for Password Change");
        simpleMessage.setText(otpFinal);

        mailSender.send(simpleMessage);

        return otpFinal;

    }

}
