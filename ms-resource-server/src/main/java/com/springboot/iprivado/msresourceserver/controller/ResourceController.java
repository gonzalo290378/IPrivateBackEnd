package com.springboot.iprivado.msresourceserver.controller;

import com.springboot.iprivado.msresourceserver.dto.MessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/resource")
@CrossOrigin(origins = "http://localhost:4200")
public class ResourceController {

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> user(JwtAuthenticationToken jwtAuth) {
        return ResponseEntity.ok(jwtAuth.getTokenAttributes());
    }
}
