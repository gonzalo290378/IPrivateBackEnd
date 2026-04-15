package com.iprivado.messages.dto;

import com.iprivado.messages.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private String id;

    private String senderId;

    private String receiverId;

    private String conversationId;

    private String body;

    private Instant createdAt;

    private MessageStatus status;


}