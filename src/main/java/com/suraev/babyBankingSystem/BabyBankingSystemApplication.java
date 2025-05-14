package com.suraev.babyBankingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BabyBankingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BabyBankingSystemApplication.class, args);
	}

}
