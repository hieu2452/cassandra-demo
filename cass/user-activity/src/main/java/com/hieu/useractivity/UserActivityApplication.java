package com.hieu.useractivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UserActivityApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserActivityApplication.class, args);
	}

}
