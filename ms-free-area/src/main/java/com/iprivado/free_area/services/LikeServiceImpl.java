package com.iprivado.free_area.services;

import com.iprivado.free_area.dto.LikeResponseDTO;
import com.iprivado.free_area.models.entity.Like;
import com.iprivado.free_area.repositories.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Transactional
    public LikeResponseDTO toggle(Long contentId, String username) {
        if (likeRepository.existsByContentIdAndUsername(contentId, username)) {
            likeRepository.deleteByContentIdAndUsername(contentId, username);
        } else {
            likeRepository.save(Like.builder()
                    .contentId(contentId)
                    .username(username)
                    .build());
        }
        long total = likeRepository.countByContentId(contentId);
        boolean liked = likeRepository.existsByContentIdAndUsername(contentId, username);
        return new LikeResponseDTO(contentId, total, liked);
    }

    public LikeResponseDTO getStatus(Long contentId, String username) {
        long total = likeRepository.countByContentId(contentId);
        boolean liked = likeRepository.existsByContentIdAndUsername(contentId, username);
        return new LikeResponseDTO(contentId, total, liked);
    }
}
