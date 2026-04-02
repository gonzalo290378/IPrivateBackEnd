package com.iprivado.ms_follow.controllers;

import com.iprivado.ms_follow.dto.FollowResponseDTO;
import com.iprivado.ms_follow.services.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/follow")
@Slf4j
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    // Seguir a un usuario
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{followedId}")
    public ResponseEntity<Void> follow(@PathVariable Long followedId) {
        log.info("ms-follow Calling follow for followedId {}", followedId);
        followService.follow(followedId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Dejar de seguir
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{followedId}")
    public ResponseEntity<Void> unfollow(@PathVariable Long followedId) {
        log.info("ms-follow Calling unfollow for followedId {}", followedId);
        followService.unfollow(followedId);
        return ResponseEntity.noContent().build();
    }

    // Seguidores de un usuario (quien lo sigue)
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<FollowResponseDTO>> getFollowers(@PathVariable Long userId) {
        log.info("ms-follow Calling getFollowers for userId {}", userId);
        return ResponseEntity.ok(followService.getFollowers(userId));
    }

    // Seguidos de un usuario (a quién sigue)
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<FollowResponseDTO>> getFollowing(@PathVariable Long userId) {
        log.info("ms-follow Calling getFollowing for userId {}", userId);
        return ResponseEntity.ok(followService.getFollowing(userId));
    }

    // Contadores
    @GetMapping("/{userId}/count")
    public ResponseEntity<Map<String, Long>> getCounts(@PathVariable Long userId) {
        log.info("ms-follow Calling getCounts for userId {}", userId);
        return ResponseEntity.ok(Map.of(
                "followers", followService.countFollowers(userId),
                "following", followService.countFollowing(userId)
        ));
    }

    // Saber si el usuario autenticado sigue a alguien
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/is-following/{followedId}")
    public ResponseEntity<Map<String, Boolean>> isFollowing(@PathVariable Long followedId) {
        log.info("ms-follow Calling isFollowing for followedId {}", followedId);
        return ResponseEntity.ok(Map.of("following", followService.isFollowing(followedId)));
    }
}
