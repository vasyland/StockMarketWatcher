package com.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockWatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockWatcherApplication.class, args);
	}

}
