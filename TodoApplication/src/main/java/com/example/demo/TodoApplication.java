package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		String password = "a@124M_3drg";
		String hassedPassword = passwordEncoder.encode(password);
		System.out.println(hassedPassword);
		
		SpringApplication.run(TodoApplication.class, args);
	}

}
