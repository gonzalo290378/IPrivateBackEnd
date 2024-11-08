package com.iprivado.free_area.services;

import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.models.entity.FreeArea;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface FreeAreaService {

    List<FreeAreaDTO> findAll();

    Optional<FreeAreaDTO> findById(Long id);

    FreeArea save(Boolean isEnabled);

    void delete(Long id);

}