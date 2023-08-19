package com.example.demo.services;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OtpGenerationService {

    public static String generateOtp(int length) {
        String numbers = "1234567890";
        StringBuilder otp = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return otp.toString();
    }
}
