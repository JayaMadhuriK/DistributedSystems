package com.pubsub.publisher.service;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "gateway", defaultRequestChannel = "pubsubOutputChannel")
public interface PubsubOutboundGateway {
    void sendToPubsub(String text);
}