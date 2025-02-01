package com.iprivado.ms_producer.services;

import com.iprivado.ms_producer.model.MessageChat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatProducerService {

    private static final String TOPIC = "chat-topic";

    private final KafkaTemplate<String, MessageChat> kafkaTemplate;

    ChatProducerService(KafkaTemplate<String, MessageChat> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserMessage(MessageChat msg) {
        log.info(String.format("\n ===== Producing message in JSON ===== \n" + msg));
        Message<MessageChat> message = MessageBuilder
                .withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .build();
        this.kafkaTemplate.send(message);
    }

}

