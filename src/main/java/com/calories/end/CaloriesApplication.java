package com.calories.end;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CaloriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaloriesApplication.class, args);
	}

}
