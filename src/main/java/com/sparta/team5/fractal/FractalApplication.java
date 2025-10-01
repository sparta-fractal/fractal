package com.sparta.team5.fractal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FractalApplication {

	public static void main(String[] args) {
		SpringApplication.run(FractalApplication.class, args);
	}

}
