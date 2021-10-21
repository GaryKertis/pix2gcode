package com.pix2gcode.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

	@Bean
	public StorageService getStorageService() {
		return new StorageService();
	}

	@Bean
	public Minigrep getMinigrep() {
		return new Minigrep();
	}

}
