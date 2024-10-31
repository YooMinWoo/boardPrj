package com.example.noticePrj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class NoticePrjApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoticePrjApplication.class, args);
	}

}
