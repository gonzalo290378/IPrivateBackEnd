package com.iprivado.free_area.services;

import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.dto.PrincipalPhotoDTO;
import com.iprivado.free_area.dto.PublicContentDTO;
import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.models.entity.PrincipalPhoto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FreeAreaService {

    List<FreeAreaDTO> findAll();

    Optional<FreeAreaDTO> findById(Long id);

    Optional<PrincipalPhotoDTO> getPrincipalPhoto(Long id);

    Optional<List<PublicContentDTO>> getPublicContent(Long id, Long lastId, int limit);

    Optional<PublicContentDTO> updatePublicContent(Long id, Long contentId, PublicContentDTO publicContentDTO);

    FreeArea save(Boolean isEnabled);

    List<PublicContentDTO> addPublicContent(Long id, String descripcion, List<MultipartFile> files);

    void delete(Long id);

    void deletePublicContent(Long id, Long idContent);

    PrincipalPhoto addPrincipalPhoto(MultipartFile file, Long idFreeArea);

    void reactivateUser(Long id);
}