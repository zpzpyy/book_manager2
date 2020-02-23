package com.enliple.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BookManagerActor {

	public static void main(String[] args) {
		SpringApplication.run(BookManagerActor.class, args);
	}

}
