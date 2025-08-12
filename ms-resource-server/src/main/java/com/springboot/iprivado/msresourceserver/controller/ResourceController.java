package com.springboot.iprivado.msresourceserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping("/user")
    public ResponseEntity<?> user(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.ok(Map.of("message", "Usuario no autenticado", "authenticated", false));
        }

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Map<String, Object> tokenAttributes = jwtAuth.getTokenAttributes();
            return ResponseEntity.ok(Map.of(
                    "user", tokenAttributes,
                    "authenticated", true
            ));
        }

        return ResponseEntity.ok(Map.of("message", "Usuario autenticado", "authenticated", true));
    }

    @GetMapping("/protected")
    @PreAuthorize("hasAuthority('SCOPE_openid')")
    public ResponseEntity<?> protectedEndpoint(JwtAuthenticationToken jwtAuth) {
        Map<String, Object> tokenAttributes = jwtAuth.getTokenAttributes();
        return ResponseEntity.ok(tokenAttributes);
    }
}
