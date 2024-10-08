package com.ceos20.spring_instagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // JPA Auditing 활성화
public class SpringInstagramApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringInstagramApplication.class, args);
	}

}
