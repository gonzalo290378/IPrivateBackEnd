package com.iprivado.messages.services;

import com.iprivado.messages.clients.FreeAreaClientRest;
import com.iprivado.messages.clients.UserClientRest;
import com.iprivado.messages.dto.*;
import com.iprivado.messages.entity.Message;
import com.iprivado.messages.enums.MessageStatus;
import com.iprivado.messages.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserClientRest userClientRest;

    private final FreeAreaClientRest freeAreaClientRest;

    private final SimpMessagingTemplate messagingTemplate;

    private final MongoTemplate mongoTemplate;

    @Value("${freearea.internal-token}")
    private String internalToken;

    @Override
    public void sendMessage(MessageDTO messageDTO) {

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

        long unread = calculateUnreadConversations(message.getReceiverId());

        messagingTemplate.convertAndSend(
                "/topic/unread/" + message.getReceiverId(),
                unread
        );
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

        long unread = calculateUnreadConversations(dto.getViewerId());

        messagingTemplate.convertAndSend(
                "/topic/unread/" + dto.getViewerId(),
                unread
        );
    }

    @Override
    public List<ConversationSummaryDTO> getConversations(String username) {
        List<Message> allMessages = messageRepository.findAllByUserId(username);

        Map<String, Message> latestByConversation = new LinkedHashMap<>();
        for (Message msg : allMessages) {
            latestByConversation.putIfAbsent(msg.getConversationId(), msg);
        }

        return latestByConversation.values().stream()
                .map(msg -> {
                    String otherUsername = msg.getSenderId().equals(username)
                            ? msg.getReceiverId()
                            : msg.getSenderId();

                    log.info("username actual: {}", username);
                    log.info("senderId: {}", msg.getSenderId());
                    log.info("receiverId: {}", msg.getReceiverId());
                    log.info("otherUsername calculado: {}", otherUsername);

                    String photoUrl = null;
                    Long otherUserId = null;  // agregar esto
                    try {
                        UserSummaryDTO user = userClientRest.findByUsername(otherUsername).getBody();
                        if (user != null) {
                            otherUserId = user.getId();  // agregar esto
                            if (user.getIdFreeArea() != null) {
                                photoUrl = freeAreaClientRest
                                        .getPrincipalPhoto(user.getIdFreeArea(), internalToken)
                                        .map(PrincipalPhotoDTO::getUrl)
                                        .orElse(null);
                            }
                        }
                    } catch (Exception e) {
                        log.warn("Could not find photo for username {}: {}", otherUsername, e.getMessage());
                    }

                    return ConversationSummaryDTO.builder()
                            .otherUsername(otherUsername)
                            .otherUserId(otherUserId)
                            .lastMessage(msg.getBody())
                            .lastMessageDate(msg.getCreatedAt())
                            .conversationId(msg.getConversationId())
                            .profilePhotoUrl(photoUrl)
                            .unreadCount(messageRepository
                                    .countByConversationIdAndReceiverIdAndStatusNot(
                                            msg.getConversationId(),
                                            username,
                                            MessageStatus.SEEN))
                            .build();
                })
                .toList();
    }

    @Override
    public long getTotalUnread(String username) {
        return messageRepository
                .countDistinctConversationIdByReceiverIdAndStatusNot(
                        username,
                        MessageStatus.SEEN
                );
    }

    private long calculateUnreadConversations(String userId) {

        return messageRepository
                .findByReceiverIdAndStatusNot(userId, MessageStatus.SEEN)
                .stream()
                .map(Message::getConversationId)
                .distinct()
                .count();
    }


}
