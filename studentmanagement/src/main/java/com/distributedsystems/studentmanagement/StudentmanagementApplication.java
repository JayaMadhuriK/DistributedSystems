package com.distributedsystems.studentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class StudentmanagementApplication {
  
    @Bean
    public WebClient webClient() {
      return WebClient.builder().build();
    } 
    
	public static void main(String[] args) {
		SpringApplication.run(StudentmanagementApplication.class, args);
	}
}
