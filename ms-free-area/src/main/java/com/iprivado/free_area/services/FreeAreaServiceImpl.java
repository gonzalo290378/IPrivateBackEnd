package com.iprivado.free_area.services;

import com.iprivado.free_area.clients.UserClientRest;
import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.dto.PrincipalPhotoDTO;
import com.iprivado.free_area.dto.PublicContentDTO;
import com.iprivado.free_area.exceptions.FreeAreaNotFoundException;
import com.iprivado.free_area.exceptions.PrincipalPhotoNotFoundException;
import com.iprivado.free_area.exceptions.PublicContentNotFoundException;
import com.iprivado.free_area.mapper.FreeAreaMapper;
import com.iprivado.free_area.mapper.PrincipalPhotoMapper;
import com.iprivado.free_area.mapper.PublicContentMapper;
import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.models.entity.PrincipalPhoto;
import com.iprivado.free_area.models.entity.PublicContent;
import com.iprivado.free_area.repositories.FreeAreaRepository;
import com.iprivado.free_area.repositories.PrincipalPhotoRepository;
import com.iprivado.free_area.repositories.PublicContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.iprivado.free_area.enums.DateConfiguration.TODAY;
import static com.iprivado.free_area.enums.StateConfiguration.DISABLED;
import static com.iprivado.free_area.enums.StateConfiguration.ENABLED;

@Service
public class FreeAreaServiceImpl implements FreeAreaService {

    private final FreeAreaRepository freeAreaRepository;

    private final PrincipalPhotoRepository principalPhotoRepository;

    private final PublicContentRepository publicContentRepository;

    private final FreeAreaMapper freeAreaMapper;

    private final PrincipalPhotoMapper principalPhotoMapper;

    private final PublicContentMapper publicContentMapper;

    public FreeAreaServiceImpl(FreeAreaRepository freeAreaRepository, UserClientRest userClientRest, PrincipalPhotoRepository principalPhotoRepository, PublicContentRepository publicContentRepository, FreeAreaMapper freeAreaMapper, PrincipalPhotoMapper principalPhotoMapper, PublicContentMapper publicContentMapper) {
        this.freeAreaRepository = freeAreaRepository;
        this.principalPhotoRepository = principalPhotoRepository;
        this.publicContentRepository = publicContentRepository;
        this.freeAreaMapper = freeAreaMapper;
        this.principalPhotoMapper = principalPhotoMapper;
        this.publicContentMapper = publicContentMapper;
    }

    @Transactional(readOnly = true)
    public List<FreeAreaDTO> findAll() {
        List<FreeArea> freeArea = freeAreaRepository.findAll();
        return freeArea.stream()
                .map(freeAreaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<PrincipalPhotoDTO> getPrincipalPhoto(Long id) {
        FreeArea freeArea = freeAreaRepository.findById(id)
                .orElseThrow(() -> new FreeAreaNotFoundException("FreeArea with id " + id + " not found"));
        PrincipalPhotoDTO principalPhotoDTO = principalPhotoMapper.toDTO(freeArea.getPrincipalPhoto().get(0));
        return Optional.ofNullable(principalPhotoDTO);
    }

    @Transactional()
    public PrincipalPhoto editPrincipalPhoto(Long id, String principalPhotoUrl) {
        FreeArea freeArea = freeAreaRepository.findById(id)
                .orElseThrow(() -> new FreeAreaNotFoundException("FreeArea with id " + id + " not found"));
        PrincipalPhoto principalPhoto = freeArea.getPrincipalPhoto().stream()
                .findFirst()
                .orElseThrow(() -> new PrincipalPhotoNotFoundException("PrincipalPhoto with for FreeArea id " + id + "not found"));
        principalPhoto.setUrl(principalPhotoUrl);
        principalPhoto.setUpdatedAt(TODAY.getValue());
        return principalPhotoRepository.save(principalPhoto);
    }

    @Transactional(readOnly = true)
    public Optional<List<PublicContentDTO>> getPublicContent(Long id, Long lastId, int limit) {
        FreeArea freeArea = freeAreaRepository.findById(id)
                .orElseThrow(() -> new FreeAreaNotFoundException("FreeArea with id " + id + " not found"));

        return Optional.of(freeArea.getPublicContent().stream()
                .filter(publicContent -> lastId == null || publicContent.getId() < lastId)
                .sorted(Comparator.comparing(PublicContent::getId).reversed())
                .limit(limit)
                .map(publicContentMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @Transactional()
    public Optional<PublicContentDTO> updatePublicContent(Long id, Long idContent, PublicContentDTO publicContentDTO) {
        FreeArea freeArea = freeAreaRepository.findById(id)
                .orElseThrow(() -> new FreeAreaNotFoundException("FreeArea with id" + id + " not found"));

        PublicContent content = publicContentRepository.findByIdAndFreeAreaId(idContent, freeArea.getId())
                .orElseThrow(() -> new PublicContentNotFoundException("PublicContent idContent" + idContent + "not found"));

        content.setDescription(publicContentDTO.getDescription());
        content.setContentUrl(publicContentDTO.getContentUrl());
        content.setIsEnabled(publicContentDTO.getIsEnabled());
        content.setLikesCount(0L);
        content.setUpdatedAt(TODAY.getValue());

        return Optional.ofNullable(publicContentMapper.toDTO(publicContentRepository.save(content)));
    }


    @Transactional(readOnly = true)
    public Optional<FreeAreaDTO> findById(Long id) {
        return Optional.ofNullable(freeAreaRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .map(freeAreaMapper::toDTO)
                .findFirst().orElseThrow(() ->
                        new FreeAreaNotFoundException("FreeArea with id " + id + " not found")));
    }

    @Transactional
    public FreeArea save(Boolean isEnabled) {
        FreeArea freeArea = FreeArea.builder()
                .isEnabled(isEnabled)
                .principalPhoto(new ArrayList<>())
                .publicContent(new ArrayList<>())
                .build();
        freeAreaRepository.save(freeArea);
        return freeArea;
    }

    @Transactional
    public PrincipalPhoto savePrincipalPhoto(Long id, String principalPhotoUrl) {
        FreeArea freeArea = freeAreaRepository.findById(id)
                .orElseThrow(() -> new FreeAreaNotFoundException("FreeArea with id " + id + " not found"));

        if (freeArea.getPrincipalPhoto().stream().anyMatch(photo -> photo.getIsEnabled().equals(DISABLED.getValue()) || photo.getIsEnabled() == ENABLED.getValue())) {
            PrincipalPhoto principalPhoto = freeArea.getPrincipalPhoto().get(0);
            principalPhoto.setUrl(principalPhotoUrl);
            principalPhoto.setIsEnabled(ENABLED.getValue());
            principalPhoto.setUpdatedAt(TODAY.getValue());
            return principalPhotoRepository.save(principalPhoto);
        }

        PrincipalPhoto principalPhoto = PrincipalPhoto.builder()
                .freeArea(freeArea)
                .isEnabled(ENABLED.getValue())
                .url(principalPhotoUrl)
                .createdAt(TODAY.getValue())
                .updatedAt(TODAY.getValue())
                .build();

        return principalPhotoRepository.save(principalPhoto);
    }

    @Transactional
    public PublicContent addPublicContent(Long id, PublicContentDTO publicContentDTO) {
        FreeArea freeArea = freeAreaRepository.findById(id)
                .orElseThrow(() -> new FreeAreaNotFoundException("FreeArea with id " + id + " not found"));

        PublicContent publicContent = PublicContent.builder()
                .freeArea(freeArea)
                .isEnabled(ENABLED.getValue())
                .description(publicContentDTO.getDescription())
                .contentUrl(publicContentDTO.getContentUrl())
                .likesCount(0L)
                .createdAt(TODAY.getValue())
                .updatedAt(TODAY.getValue())
                .build();

        freeArea.getPublicContent().add(publicContent);
        freeAreaRepository.save(freeArea);

        return publicContent;
    }

    @Transactional
    public void delete(Long id) {
        Optional<FreeArea> freeArea = freeAreaRepository.findById(id);
        if (freeArea.isEmpty()) {
            throw new FreeAreaNotFoundException("Free Area was not found with id:" + id);
        }
        Optional<Long> idPrincipalPhoto = freeArea.get().getPrincipalPhoto().stream()
                .findFirst()
                .map(PrincipalPhoto::getId);

        Optional.ofNullable(freeArea.get().getPublicContent())
                .ifPresent(publicContents ->
                        publicContents.forEach(pc -> deletePublicContent(id, pc.getId()))
                );

        idPrincipalPhoto.ifPresent(this::deletePrincipalPhoto);
        freeAreaRepository.logicDelete(id);
    }

    @Transactional
    public void deletePrincipalPhoto(Long idPrincipalPhoto) {
        principalPhotoRepository.logicalDelete(idPrincipalPhoto);
    }

    @Transactional
    public void deletePublicContent(Long id, Long idContent) {
        publicContentRepository.logicalDelete(id, idContent);
    }

}
