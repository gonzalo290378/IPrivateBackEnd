package com.iprivado.messages.repositories;

import com.iprivado.messages.entity.Message;
import com.iprivado.messages.enums.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByConversationIdOrderByCreatedAtAsc(String conversationId);

    List<Message> findBySenderIdOrReceiverIdOrderByCreatedAtDesc(String senderId, String receiverId);

    default List<Message> findAllByUserId(String userId) {
        return findBySenderIdOrReceiverIdOrderByCreatedAtDesc(userId, userId);
    }

    long countDistinctConversationIdByReceiverIdAndStatusNot(
            String receiverId,
            MessageStatus status
    );

    long countByConversationIdAndReceiverIdAndStatusNot(String conversationId, String receiverId, MessageStatus status);
}

