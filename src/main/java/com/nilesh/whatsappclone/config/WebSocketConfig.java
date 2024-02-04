package com.nilesh.whatsappclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.logging.Logger;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = Logger.getLogger(WebSocketConfig.class.getName());

    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/group");
        registry.setApplicationDestinationPrefixes("/app");
        logger.info("WebSocket message broker configured.");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000/").withSockJS();
        logger.info("Stomp endpoints registered.");
        // registry.addEndpoint("/ws").withSockJS();

    }
    
}
