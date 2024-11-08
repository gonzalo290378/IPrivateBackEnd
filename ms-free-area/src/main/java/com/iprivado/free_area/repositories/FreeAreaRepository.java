package com.iprivado.free_area.repositories;

import com.iprivado.free_area.models.entity.FreeArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeAreaRepository extends JpaRepository<FreeArea, Long> {

    @Modifying
    @Query("UPDATE FreeArea fa " +
            "SET fa.isEnabled = false " +
            "WHERE fa.id = :id")

    void logicDelete(@Param("id") Long id);

}
