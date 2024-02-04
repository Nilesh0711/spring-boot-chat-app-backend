package com.nilesh.whatsappclone.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.nilesh.whatsappclone.model.Message;

import java.util.logging.Logger;

@RestController
public class RealTimeChat {

    private SimpMessagingTemplate simpMessagingTemplate;

    public RealTimeChat(SimpMessagingTemplate messagingTemplate) {
        this.simpMessagingTemplate = messagingTemplate;
    }

    private static final Logger logger = Logger.getLogger(RealTimeChat.class.getName());

    @MessageMapping("/message")
    @SendTo("/group/public")
    public Message receiveMessage(@Payload Message message) throws InterruptedException {
        String destination = "/group/" + message.getChat().getId().toString();
        simpMessagingTemplate.convertAndSend(destination, message);
        logger.info("Received message: " + message.getContent());
        return message;
    }
}
