package com.iprivado.free_area.repositories;

import com.iprivado.free_area.models.entity.PublicContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicContentRepository extends JpaRepository<PublicContent, Long> {

    @Query("SELECT pc " +
            "FROM PublicContent pc " +
            "WHERE pc.id = :idPublicContent " +
            "AND pc.freeArea.id = :id")
    Optional<PublicContent> findByIdAndFreeAreaId(@Param("idPublicContent") Long idPublicContent,
                                                  @Param("id") Long id);


    @Modifying
    @Query("UPDATE PublicContent pc " +
            "SET pc.isEnabled = false " +
            "WHERE pc.freeArea.id = :id " +
            "AND pc.id = :idContent")

    void logicalDelete(@Param("id") Long id, @Param("idContent") Long idContent);


}
