package com.tushar.completablefuture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CompletableFutureApplication {
	public static void main(String[] args) {
		SpringApplication.run(CompletableFutureApplication.class, args);
	}
}
