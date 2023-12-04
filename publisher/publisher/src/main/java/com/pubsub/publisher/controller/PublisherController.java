package com.pubsub.publisher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pubsub.publisher.service.PubsubOutboundGateway;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PublisherController {
    
    @Autowired
    private PubsubOutboundGateway messagingGateway;
    
    @PostMapping("/publishMessage")
    public String publishMessage(@RequestBody String message) {
        messagingGateway.sendToPubsub(message);
        log.info("message published by internal publisher");
        return "message published";
    }
}
