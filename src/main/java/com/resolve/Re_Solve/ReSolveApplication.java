package com.resolve.Re_Solve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReSolveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReSolveApplication.class, args);
	}

}
