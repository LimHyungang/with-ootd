package com.ootd.with;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WithOotdApplication {

	public static void main(String[] args) {
		SpringApplication.run(WithOotdApplication.class, args);
	}

}
