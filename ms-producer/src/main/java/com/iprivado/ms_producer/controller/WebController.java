package com.iprivado.ms_producer.controller;

import com.iprivado.ms_producer.model.MessageChat;
import com.iprivado.ms_producer.services.ChatProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/web")
public class WebController {

    @Autowired
    private ChatProducerService chatProducerService;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody MessageChat messageChat){
        chatProducerService.sendUserMessage(messageChat);

    }
}
