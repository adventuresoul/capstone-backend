package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class HomeEssentialsAutoReplenishmentPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeEssentialsAutoReplenishmentPlatformApplication.class, args);
	}

}
