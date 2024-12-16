package com.iprivado.ms_consumer.service;

import com.iprivado.ms_consumer.clients.UserClientRest;
import com.iprivado.ms_consumer.model.MessageChat;
import com.iprivado.ms_consumer.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ChatConsumerService {

    private final WebSocketService webSocketService;
    private final UserClientRest userClientRest;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatConsumerService.class);


    @Autowired
    public ChatConsumerService(WebSocketService webSocketService, UserClientRest userClientRest) {
        this.webSocketService = webSocketService;
        this.userClientRest = userClientRest;
    }

    @KafkaListener(topics = "chat-topic", groupId = "chat-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(MessageChat messageChat) {
        User receiver = userClientRest.getUserById(Long.valueOf(messageChat.getReceiver()));
        User sender = userClientRest.getUserById(Long.valueOf(messageChat.getSender()));
        String formattedMessage = "From: " + receiver.getUsername() + " " + receiver.getEmail() + "\n" +
                messageChat.getBody();
        webSocketService.sendMessageToUser(String.valueOf(messageChat.getReceiver()), formattedMessage);
        LOGGER.info("Received Message: " + messageChat + " " + formattedMessage);
        LOGGER.info("Receiver: " + receiver);
        LOGGER.info("Sender: " + sender);
    }
}

