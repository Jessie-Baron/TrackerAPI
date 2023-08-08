package com.cognixia.jump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Pep2ProgressTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Pep2ProgressTrackerApplication.class, args);
	}

}
