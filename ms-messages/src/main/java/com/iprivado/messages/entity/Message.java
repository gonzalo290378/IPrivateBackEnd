package com.iprivado.messages.entity;

import com.iprivado.messages.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "messages")
public class Message {

    @Id
    private String id;

    private String senderId;

    private String receiverId;

    @Indexed
    private String conversationId;

    private String body;

    private MessageStatus status;

    @Indexed
    private Instant createdAt;
}
