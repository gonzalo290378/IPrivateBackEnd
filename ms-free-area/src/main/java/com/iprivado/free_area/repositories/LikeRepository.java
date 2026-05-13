package com.iprivado.free_area.repositories;

import com.iprivado.free_area.models.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByContentIdAndUsername(Long contentId, String username);

    void deleteByContentIdAndUsername(Long contentId, String username);

    long countByContentId(Long contentId);
}
