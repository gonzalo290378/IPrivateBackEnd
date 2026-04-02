package com.iprivado.ms_follow.services;


import com.iprivado.ms_follow.dto.FollowResponseDTO;

import java.util.List;

public interface FollowService {

    void follow(Long followedId);

    void unfollow(Long followedId);

    List<FollowResponseDTO> getFollowers(Long userId);

    List<FollowResponseDTO> getFollowing(Long userId);

    long countFollowers(Long userId);

    long countFollowing(Long userId);

    boolean isFollowing(Long followedId);
}
