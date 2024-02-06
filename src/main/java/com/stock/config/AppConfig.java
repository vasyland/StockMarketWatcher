package com.stock.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.stock.repositories")
@EntityScan(basePackages = "com.stock.model")
public class AppConfig {
}
