package com.pubsub.externalsubscriber.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class PubSubSubscriberConfig {
    
    private final WebClient webClient;

    public PubSubSubscriberConfig(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    
    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
      @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
      PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
          new PubSubInboundChannelAdapter(pubSubTemplate, "placement-management");
        adapter.setOutputChannel(inputChannel);
        log.info("messageChannelAdapter invoked");
        return adapter;
    }
    
    @Bean
    public MessageChannel pubsubInputChannel() {
        log.info("pubsubInputChannel invoked");
        return new DirectChannel();
    }
    
    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public MessageHandler messageReceiver() {
        log.info("messageReceiver invoked");
        return message -> {
          log.info("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
          String companyName = new String((byte[]) message.getPayload());
          try {
              webClient.post()
              .uri("http://localhost:6064/publishMessage")
              .bodyValue(companyName)
              .retrieve()
              .bodyToMono(String.class)
              .block();
          }catch (Exception e) {
              log.error("Error sending POST request: " + e.getMessage(), e);
          }
        };
    }
    
}
