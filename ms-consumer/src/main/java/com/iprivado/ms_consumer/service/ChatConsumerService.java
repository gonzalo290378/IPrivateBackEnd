package com.iprivado.ms_consumer.service;

import com.iprivado.ms_consumer.model.MessageChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatConsumerService {

    private final Logger LOGGER = LoggerFactory.getLogger(ChatConsumerService.class);

    @KafkaListener(topics = "chat-topic", containerFactory = "kafkaListenerContainerFactory")
    public void consumeUserMessage(@Payload MessageChat messageChat, @Headers MessageHeaders headers) throws IOException {
        System.out.println("received data in Consumer One =" + messageChat);
    }


}

