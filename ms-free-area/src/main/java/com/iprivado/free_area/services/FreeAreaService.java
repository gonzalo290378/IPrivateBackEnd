package com.iprivado.free_area.services;

import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.dto.PrincipalPhotoDTO;
import com.iprivado.free_area.dto.PublicContentDTO;
import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.models.entity.PrincipalPhoto;
import com.iprivado.free_area.models.entity.PublicContent;

import java.util.List;
import java.util.Optional;

public interface FreeAreaService {

    List<FreeAreaDTO> findAll();

    Optional<FreeAreaDTO> findById(Long id);

    Optional<PrincipalPhotoDTO> getPrincipalPhoto(Long id);

    PrincipalPhoto editPrincipalPhoto(Long id, String principalPhoto);

    Optional<List<PublicContentDTO>> getPublicContent(Long id, Long lastId, int limit);

    Optional<PublicContentDTO> updatePublicContent(Long id, Long contentId, PublicContentDTO publicContentDTO);

    FreeArea save(Boolean isEnabled);

    PrincipalPhoto savePrincipalPhoto(Long id, String principalPhotoUrl);

    PublicContent addPublicContent(Long id, PublicContentDTO publicContentDTO);

    void delete(Long id);

    void deletePrincipalPhoto(Long id);

    void deletePublicContent(Long id, Long idContent);

}