package com.xm.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptoServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoServicesApplication.class, args);
	}

}
