package com.toy.chanygram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ChanygramApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChanygramApplication.class, args);
	}

}
