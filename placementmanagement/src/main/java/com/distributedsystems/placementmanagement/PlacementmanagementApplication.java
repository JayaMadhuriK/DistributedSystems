package com.distributedsystems.placementmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class PlacementmanagementApplication {
    
    @Bean
    public WebClient webClient() {
      return WebClient.builder().build();
    }
    
	public static void main(String[] args) {
		SpringApplication.run(PlacementmanagementApplication.class, args);
	}

}
