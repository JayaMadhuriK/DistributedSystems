package com.pubsub.externalsubscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ExternalsubscriberApplication {
    
    @Bean
    public WebClient webClient() {
      return WebClient.builder().build();
    }
    
	public static void main(String[] args) {
		SpringApplication.run(ExternalsubscriberApplication.class, args);
	}

}
