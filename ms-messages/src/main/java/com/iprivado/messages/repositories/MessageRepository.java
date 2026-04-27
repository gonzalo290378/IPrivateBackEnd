package com.iprivado.messages.repositories;

import com.iprivado.messages.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByConversationIdOrderByCreatedAtAsc(String conversationId);

    List<Message> findBySenderIdOrReceiverIdOrderByCreatedAtDesc(String senderId, String receiverId);

    default List<Message> findAllByUserId(String userId) {
        return findBySenderIdOrReceiverIdOrderByCreatedAtDesc(userId, userId);
    }
}

