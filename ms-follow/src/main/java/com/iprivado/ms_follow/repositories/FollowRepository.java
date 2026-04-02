package com.iprivado.ms_follow.repositories;

import com.iprivado.ms_follow.models.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerIdAndFollowedId(Long followerId, Long followedId);

    List<Follow> findByFollowedId(Long followedId);

    List<Follow> findByFollowerId(Long followerId);

    long countByFollowedId(Long followedId);

    long countByFollowerId(Long followerId);

    void deleteByFollowerIdAndFollowedId(Long followerId, Long followedId);
}
