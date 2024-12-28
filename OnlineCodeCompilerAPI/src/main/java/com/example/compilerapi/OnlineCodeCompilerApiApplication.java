package com.example.compilerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class OnlineCodeCompilerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineCodeCompilerApiApplication.class, args);
	}

}
