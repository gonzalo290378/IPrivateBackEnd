package com.iprivado.ms_producer.services;

import com.iprivado.ms_producer.model.MessageChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ChatProducerService {

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatProducerService.class);
    private static final String TOPIC = "chat-topic";

    @Autowired
    private KafkaTemplate<String, MessageChat> kafkaTemplate;

    public void sendUserMessage(MessageChat msg) {
        LOGGER.info(String.format("\n ===== Producing message in JSON ===== \n" + msg));
        Message<MessageChat> message = MessageBuilder
                .withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .build();
        this.kafkaTemplate.send(message);
    }

}

