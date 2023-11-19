package com.stock.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import jakarta.persistence.EntityManagerFactory;

@Configuration
// @ComponentScan("com.stock")
// @ComponentScan
@EnableJpaRepositories(basePackages = "com.stock.repositories")
@EntityScan(basePackages = "com.stock.model")
public class AppConfig {

//   @Bean(name = "entityManagerFactory")
//   public EntityManagerFactory  entityManagerFactory() {
//	   
//   LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//  
//   return sessionFactory;
//  }

}
