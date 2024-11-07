package com.example.boardPrj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BoardPrjApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardPrjApplication.class, args);
	}

}
