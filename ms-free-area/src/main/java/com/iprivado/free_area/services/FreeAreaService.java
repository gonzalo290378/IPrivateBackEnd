package com.iprivado.free_area.services;

import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.dto.PrincipalPhotoDTO;
import com.iprivado.free_area.dto.PublicContentDTO;
import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.models.entity.PrincipalPhoto;

import java.util.List;
import java.util.Optional;

public interface FreeAreaService {

    List<FreeAreaDTO> findAll();

    Optional<FreeAreaDTO> findById(Long id);

    Optional<PrincipalPhotoDTO> getPrincipalPhoto(Long id);

    PrincipalPhoto editPrincipalPhoto(Long id, String principalPhoto);

    Optional<List<PublicContentDTO>> getPublicContent(Long id, Long lastId, int limit);

    FreeArea save(Boolean isEnabled);

    void delete(Long id);

}