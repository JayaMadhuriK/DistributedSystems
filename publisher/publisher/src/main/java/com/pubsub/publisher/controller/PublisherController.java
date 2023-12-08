package com.pubsub.publisher.controller;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pubsub.publisher.service.PubsubOutboundGateway;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PublisherController {
    
    @Autowired
    private PubsubOutboundGateway messagingGateway;
    private final Tracer tracer;
    
    public PublisherController(Tracer tracer) {
        this.tracer = tracer;
    }
    @PostMapping("/publishMessage")
    public String publishMessage(@RequestBody String message) {
        String traceId = retrieveTraceId();
        Message<String> pubsubMessage = MessageBuilder
                .withPayload(message)
                .setHeader("trace-id", traceId)
                .build();
        messagingGateway.sendToPubsub(pubsubMessage);
        MDC.put("payload", message);
        log.info("Payload : "+MDC.get("payload"));
        log.info("message published to the gcp topic");
        return "message published";
    }
    private String retrieveTraceId() {
        String traceId = tracer.currentSpan().context().traceIdString();
        return traceId;
    }
}
