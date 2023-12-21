package com.pubsub.externalsubscriber.config;

import java.util.NoSuchElementException;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.pubsub.externalsubscriber.dto.CustomResponse;

import brave.Tracer;
import brave.propagation.TraceContext;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class PubSubSubscriberConfig {
    
    @Value("${pubsub.subscription-name}")
    private String subscriptionName;
    private final WebClient webClient;

    public PubSubSubscriberConfig(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    
    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
      @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
      PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
          new PubSubInboundChannelAdapter(pubSubTemplate, subscriptionName);
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
    public MessageHandler messageReceiver(Tracer tracer, MeterRegistry meterRegistry) {
        return message -> {
          log.info("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
          String companyName = new String((byte[]) message.getPayload());
    
          try {
              String customTraceId = (String) message.getHeaders().get("trace-id");
              if (customTraceId != null) {
                  TraceContext traceContext = TraceContext.newBuilder()
                          .traceId(Long.parseUnsignedLong(customTraceId, 16))
                          .spanId(1)
                          .build();
                  MDC.put("execution-Id", traceContext.traceIdString());
                  tracer.withSpanInScope(tracer.newChild(traceContext));
                  meterRegistry.config().commonTags("traceId", customTraceId);
              }else {
                  throw new NoSuchElementException();
              }
              webClient.post()
              .uri("http://localhost:6064/publishMessage")
              .bodyValue(companyName)
              .retrieve()
              .onStatus(
                      status -> status.is4xxClientError() || status.is5xxServerError(),
                      response -> response.bodyToMono(CustomResponse.class)
                      .map(body -> new Exception(body.getMessage())
                      ))
              .bodyToMono(String.class)
              .block();
              log.info("Message sent to internal publisher");
          }
          catch(NoSuchElementException ex) {
              log.info("Provide proper trace id with less or equal to 16 numbers");
          }
          catch(Exception ex) {
              log.info("internal publisher is inactive");
          }finally {
              MDC.clear();
          }
        };
    }
    
}
