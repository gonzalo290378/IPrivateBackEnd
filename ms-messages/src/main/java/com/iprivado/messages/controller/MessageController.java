package com.iprivado.messages.controller;

import com.iprivado.messages.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import com.iprivado.messages.services.MessageService;

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
}
