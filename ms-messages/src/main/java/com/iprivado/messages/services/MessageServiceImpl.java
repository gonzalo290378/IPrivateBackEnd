package com.iprivado.messages.services;

import com.iprivado.messages.dto.MessageDTO;
import com.iprivado.messages.enums.MessageStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.iprivado.messages.entity.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.iprivado.messages.repositories.MessageRepository;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public Message sendMessage(MessageDTO messageDTO) {

        String conversationId = buildConversationId(
                messageDTO.getSenderId(),
                messageDTO.getReceiverId()
        );

        Message message = Message.builder()
                .senderId(messageDTO.getSenderId())
                .receiverId(messageDTO.getReceiverId())
                .conversationId(conversationId)
                .body(messageDTO.getBody())
                .status(MessageStatus.SENT)
                .createdAt(Instant.now())
                .build();

        messageRepository.save(message);

        messagingTemplate.convertAndSend(
                "/topic/conversations/" + conversationId,
                toDTO(message)
        );

        return message;
    }

    @Override
    public List<MessageDTO> getConversation(String senderId, String receiverId) {

        String conversationId = buildConversationId(senderId, receiverId);

        return messageRepository
                .findByConversationIdOrderByCreatedAtAsc(conversationId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private MessageDTO toDTO(Message message) {
        return MessageDTO.builder()
                .senderId(message.getSenderId())
                .id(message.getId())
                .conversationId(message.getConversationId())
                .receiverId(message.getReceiverId())
                .createdAt(message.getCreatedAt())
                .status(message.getStatus())
                .body(message.getBody())
                .build();
    }

    private String buildConversationId(String id1, String id2) {
        return id1.compareTo(id2) < 0
                ? id1 + "_" + id2
                : id2 + "_" + id1;
    }
}
