package com.iprivado.free_area.repositories;

import com.iprivado.free_area.models.entity.PrincipalPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalPhotoRepository extends JpaRepository<PrincipalPhoto, Long> {

    @Modifying
    @Query("UPDATE PrincipalPhoto p " +
            "SET p.isEnabled = false" +
            " WHERE p.id = :id")

    void logicalDelete(@Param("id") Long id);
}
