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
public class ConversationSummaryDTO {

    private String otherUsername;

    private Long otherUserId;

    private String lastMessage;

    private Instant lastMessageDate;

    private String conversationId;

    private String profilePhotoUrl;

}
