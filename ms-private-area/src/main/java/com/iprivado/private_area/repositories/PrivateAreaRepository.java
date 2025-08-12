package com.iprivado.private_area.repositories;

import com.iprivado.private_area.models.entity.PrivateArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateAreaRepository extends JpaRepository<PrivateArea, Long> {

    @Modifying
    @Query("UPDATE PrivateArea pa " +
            "SET pa.isEnabled = false " +
            "WHERE pa.id = :id")
    void logicDelete(@Param("id") Long id);

}
