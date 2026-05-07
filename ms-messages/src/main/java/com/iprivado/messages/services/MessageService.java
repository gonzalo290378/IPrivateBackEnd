package com.iprivado.messages.services;

import com.iprivado.messages.dto.ConversationSummaryDTO;
import com.iprivado.messages.dto.MessageDTO;
import com.iprivado.messages.dto.SeenDTO;

import java.util.List;

public interface MessageService {

    void sendMessage(MessageDTO messageDTO);

    List<MessageDTO> getConversation(String senderId, String receiverId);

    void markAsSeen(SeenDTO dto);

    String buildConversationId(String user1, String user2);

    List<ConversationSummaryDTO> getConversations(String username);

    long getTotalUnread(String username);


}
