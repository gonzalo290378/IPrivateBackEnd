package com.iprivado.messages.services;

import com.iprivado.messages.dto.MessageDTO;
import com.iprivado.messages.dto.SeenDTO;
import com.iprivado.messages.entity.Message;

import java.util.List;

public interface MessageService {

    Message sendMessage(MessageDTO messageDTO);

    List<MessageDTO> getConversation(String senderId, String receiverId);

    void markAsSeen(SeenDTO dto);
}
