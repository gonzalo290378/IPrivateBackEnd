package com.iprivado.free_area.services;

import com.iprivado.free_area.models.entity.FreeArea;
import java.util.List;
import java.util.Optional;

public interface FreeAreaService {

    List<FreeArea> findAll();
    Optional<FreeArea> findById(Long id);
    FreeArea save(Boolean isEnabled);

}
