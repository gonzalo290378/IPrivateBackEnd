package com.iprivado.private_area.services;

import com.iprivado.private_area.dto.PrivateAreaDTO;
import com.iprivado.private_area.models.entity.PrivateArea;

import java.util.List;
import java.util.Optional;

public interface PrivateAreaService {

    List<PrivateAreaDTO> findAll();

    Optional<PrivateAreaDTO> findById(Long id);

    PrivateArea save(Boolean isEnabled);

    void delete(Long id);


}
