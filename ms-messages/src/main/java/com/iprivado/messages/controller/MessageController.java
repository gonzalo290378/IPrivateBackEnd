package com.iprivado.messages.controller;

import com.iprivado.messages.dto.ConversationSummaryDTO;
import com.iprivado.messages.dto.MessageDTO;
import com.iprivado.messages.dto.SeenDTO;
import com.iprivado.messages.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        messageService.sendMessage(messageDTO);
    }

    @GetMapping("/{senderId}/{receiverId}")
    @ResponseBody
    public ResponseEntity<List<MessageDTO>> getConversation(
            @PathVariable String senderId,
            @PathVariable String receiverId) {
        return ResponseEntity.ok(messageService.getConversation(senderId, receiverId));
    }

    @MessageMapping("/chat.seen")
    public void markAsSeen(@Payload SeenDTO dto) {
        messageService.markAsSeen(dto);
    }

    @GetMapping("/conversation-id")
    public String getConversationId(
            @RequestParam String user1,
            @RequestParam String user2
    ) {
        return messageService.buildConversationId(user1, user2);
    }

    @GetMapping("/conversations/{username}")
    public ResponseEntity<List<ConversationSummaryDTO>> getConversations(
            @PathVariable String username) {
        return ResponseEntity.ok(messageService.getConversations(username));
    }

}
