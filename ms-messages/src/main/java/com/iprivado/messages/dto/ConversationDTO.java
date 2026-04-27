package com.iprivado.messages.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {

    private String otherUserId;

    private String lastMessage;

    private Instant lastMessageTime;

    private long unreadCount;


}