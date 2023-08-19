package com.example.demo.services;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class UserRegisterOtpService {

    public String SendRegisterOtp(int len) {

        String numbers = "123456789";

        StringBuilder sb = new StringBuilder(len);

        Random random = new Random();

        for (int i = 0; i < len; i++) {
            sb.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return sb.toString();

    }

}
