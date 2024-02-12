package com.nilesh.whatsappclone.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilesh.whatsappclone.model.Chat;
import com.nilesh.whatsappclone.model.Message;
import com.nilesh.whatsappclone.model.User;

import io.jsonwebtoken.io.IOException;

import java.util.Map;
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

    @MessageMapping("/message/typing")
    @SendTo("/group/typing/public")
    public User userTypingMessage(@Payload String payload)
            throws InterruptedException, JsonMappingException, JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> payloadMap = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
            });

            User user = objectMapper.convertValue(payloadMap.get("user"), User.class);
            Chat chat = objectMapper.convertValue(payloadMap.get("chat"), Chat.class);

            String destination = "/group/typing/" + chat.getId().toString();
            simpMessagingTemplate.convertAndSend(destination, user);

            logger.info("Received user Typing: " + user.getEmail());
            logger.info("Received user Typing: " + chat.getId());

            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
