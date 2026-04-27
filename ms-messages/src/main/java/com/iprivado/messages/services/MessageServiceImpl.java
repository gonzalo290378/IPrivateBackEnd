package com.iprivado.messages.services;

import com.iprivado.messages.dto.ConversationSummaryDTO;
import com.iprivado.messages.dto.MessageDTO;
import com.iprivado.messages.dto.SeenDTO;
import com.iprivado.messages.entity.Message;
import com.iprivado.messages.enums.MessageStatus;
import com.iprivado.messages.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final MongoTemplate mongoTemplate;

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

    public String buildConversationId(String id1, String id2) {
        return id1.compareTo(id2) < 0
                ? id1 + "_" + id2
                : id2 + "_" + id1;
    }

    @Override
    public void markAsSeen(SeenDTO dto) {

        Query query = new Query();
        query.addCriteria(
                Criteria.where("conversationId").is(dto.getConversationId())
                        .and("senderId").ne(dto.getViewerId())
                        .and("status").ne(MessageStatus.SEEN)
        );

        Update update = new Update();
        update.set("status", MessageStatus.SEEN);

        mongoTemplate.updateMulti(query, update, Message.class);

        messagingTemplate.convertAndSend(
                "/topic/conversations/" + dto.getConversationId(),
                Map.of(
                        "type", "SEEN",
                        "conversationId", dto.getConversationId(),
                        "viewerId", dto.getViewerId()
                )
        );
    }

    @Override
    public List<ConversationSummaryDTO> getConversations(String username) {
        List<Message> allMessages = messageRepository.findAllByUserId(username);

        Map<String, Message> latestByConversation = new HashMap<>();

        for (Message msg : allMessages) {
            latestByConversation.merge(
                    msg.getConversationId(),
                    msg,
                    (existing, incoming) -> incoming.getCreatedAt().isAfter(existing.getCreatedAt())
                            ? incoming
                            : existing
            );
        }

        return latestByConversation.values().stream()
                .sorted(Comparator.comparing(Message::getCreatedAt).reversed())
                .map(msg -> {
                    String otherUserId = msg.getSenderId().equals(username)
                            ? msg.getReceiverId()
                            : msg.getSenderId();
                    return ConversationSummaryDTO.builder()
                            .otherUsername(otherUserId)
                            .lastMessage(msg.getBody())
                            .lastMessageDate(msg.getCreatedAt())
                            .conversationId(msg.getConversationId())
                            .build();
                })
                .toList();
    }

}
