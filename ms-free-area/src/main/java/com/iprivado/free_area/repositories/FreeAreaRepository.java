package com.iprivado.free_area.repositories;

import com.iprivado.free_area.models.entity.FreeArea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeAreaRepository extends CrudRepository<FreeArea, Long> {


}
