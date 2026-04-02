package com.iprivado.ms_follow.services;


import com.iprivado.ms_follow.client.FreeAreaClientRest;
import com.iprivado.ms_follow.client.UserClientRest;
import com.iprivado.ms_follow.dto.FollowResponseDTO;
import com.iprivado.ms_follow.dto.PrincipalPhotoDTO;
import com.iprivado.ms_follow.dto.UserSummaryDTO;
import com.iprivado.ms_follow.exceptions.AlreadyFollowingException;
import com.iprivado.ms_follow.exceptions.FollowRelationShipNotFoundException;
import com.iprivado.ms_follow.exceptions.SelfFollowException;
import com.iprivado.ms_follow.models.entity.Follow;
import com.iprivado.ms_follow.repositories.FollowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserClientRest userClientRest;
    private final FreeAreaClientRest freeAreaClientRest;

    @Value("${freearea.internal-token}")
    private String internalToken;

    public FollowServiceImpl(FollowRepository followRepository,
                             UserClientRest userClientRest,
                             FreeAreaClientRest freeAreaClientRest) {
        this.followRepository = followRepository;
        this.userClientRest = userClientRest;
        this.freeAreaClientRest = freeAreaClientRest;
    }

    private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            String username = jwt.getClaimAsString("username");
            UserSummaryDTO user = userClientRest.findByUsername(username);
            return user.getId();
        }
        throw new RuntimeException("User not authenticated");
    }

    private FollowResponseDTO buildFollowResponse(Follow follow, Long userIdToFetch) {
        UserSummaryDTO user = userClientRest.findById(userIdToFetch);

        String photoUrl = freeAreaClientRest
                .getPrincipalPhoto(user.getIdFreeArea(), internalToken)
                .map(PrincipalPhotoDTO::getUrl)
                .orElse(null);

        user.setPhotoUrl(photoUrl);

        return FollowResponseDTO.builder()
                .id(follow.getId())
                .user(user)
                .createdAt(follow.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public void follow(Long followedId) {
        Long followerId = getAuthenticatedUserId();

        if (followerId.equals(followedId)) {
            throw new SelfFollowException("You cannot follow yourself");
        }

        followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .ifPresent(f -> {
                    throw new AlreadyFollowingException("Already following this user");
                });

        Follow follow = Follow.builder()
                .followerId(followerId)
                .followedId(followedId)
                .build();

        followRepository.save(follow);
        log.info("User {} followed user {}", followerId, followedId);
    }

    @Override
    @Transactional
    public void unfollow(Long followedId) {
        Long followerId = getAuthenticatedUserId();

        followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .orElseThrow(() -> new FollowRelationShipNotFoundException("Follow relationship not found"));

        followRepository.deleteByFollowerIdAndFollowedId(followerId, followedId);
        log.info("User {} unfollowed user {}", followerId, followedId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowResponseDTO> getFollowers(Long userId) {
        return followRepository.findByFollowedId(userId)
                .stream()
                .map(follow -> buildFollowResponse(follow, follow.getFollowerId()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FollowResponseDTO> getFollowing(Long userId) {
        return followRepository.findByFollowerId(userId)
                .stream()
                .map(follow -> buildFollowResponse(follow, follow.getFollowedId()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countFollowers(Long userId) {
        return followRepository.countByFollowedId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFollowing(Long userId) {
        return followRepository.countByFollowerId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFollowing(Long followedId) {
        Long followerId = getAuthenticatedUserId();
        return followRepository.findByFollowerIdAndFollowedId(followerId, followedId).isPresent();
    }
}