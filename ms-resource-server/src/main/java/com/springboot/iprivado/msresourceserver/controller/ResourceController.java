package com.springboot.iprivado.msresourceserver.controller;

import com.springboot.iprivado.msresourceserver.dto.MessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping("/user")
    public ResponseEntity<MessageDTO> user(Authentication authentication) {
        return ResponseEntity.ok(new MessageDTO("Hello " + authentication.getName()));
    }
}
