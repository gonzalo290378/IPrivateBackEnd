package com.iprivado.free_area.controllers;

import com.iprivado.free_area.dto.LikeResponseDTO;
import com.iprivado.free_area.services.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@Slf4j
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{contentId}/toggle")
    public ResponseEntity<LikeResponseDTO> toggle(
            @PathVariable Long contentId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(likeService.toggle(contentId, username));
    }

    @GetMapping("/{contentId}/status")
    public ResponseEntity<LikeResponseDTO> status(
            @PathVariable Long contentId,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(likeService.getStatus(contentId, username));
    }
}
