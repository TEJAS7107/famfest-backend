package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@SpringBootApplication
@EnableScheduling
public class BackendFullstackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendFullstackApplication.class, args);

		// UsernamePasswordAuthenticationToken data = new
		// UsernamePasswordAuthenticationToken("Tejas710", "Tejas@2");
		// System.out.println(data);
	}

}
