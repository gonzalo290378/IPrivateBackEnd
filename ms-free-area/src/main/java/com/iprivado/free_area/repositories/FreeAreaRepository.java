package com.iprivado.free_area.repositories;

import com.iprivado.free_area.models.entity.FreeArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeAreaRepository extends JpaRepository<FreeArea, Long> {


}
