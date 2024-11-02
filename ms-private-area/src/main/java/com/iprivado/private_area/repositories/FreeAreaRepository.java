package com.iprivado.free_area.repositories;

import com.iprivado.free_area.models.entity.FreeArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeAreaRepository extends JpaRepository<FreeArea, Long> {


}
