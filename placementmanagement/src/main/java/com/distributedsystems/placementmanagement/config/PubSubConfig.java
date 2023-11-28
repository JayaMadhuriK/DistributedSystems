package com.distributedsystems.placementmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.reactive.function.client.WebClient;

import com.distributedsystems.placementmanagement.entity.CustomResponse;
import com.distributedsystems.placementmanagement.entity.StudentPlacements;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class PubSubConfig {
    
    @Autowired
    private WebClient webClient;
    
    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
      @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
      PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
          new PubSubInboundChannelAdapter(pubSubTemplate, "placement-management");
        adapter.setOutputChannel(inputChannel);
        return adapter;
    }
    
    @Bean
    public MessageChannel pubsubInputChannel() {
        return new DirectChannel();
    }
    
    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public MessageHandler messageReceiver() {
        return message -> {
          log.info("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
          String companyName = new String((byte[]) message.getPayload());
          try {
              StudentPlacements studentPlacements= webClient
                  .get()
                  .uri("http://localhost:6063/"+companyName)
                  .retrieve()
                  .onStatus(
                          status -> status.is4xxClientError() || status.is5xxServerError(),
                          response -> response.bodyToMono(CustomResponse.class)
                          .map(body -> new Exception(body.getMessage())
                          ))
                  .bodyToMono(StudentPlacements.class)
                  .block();
          log.info("Placement details for company {}: {}", companyName, studentPlacements);
          }catch(Exception e) {
              log.info(e.getMessage());
          }
        };
    }
    
}