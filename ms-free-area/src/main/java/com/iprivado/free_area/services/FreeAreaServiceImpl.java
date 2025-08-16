package com.iprivado.free_area.services;

import com.iprivado.free_area.clients.UserClientRest;
import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.dto.PrincipalPhotoDTO;
import com.iprivado.free_area.dto.PublicContentDTO;
import com.iprivado.free_area.exceptions.FreeAreaNotFoundException;
import com.iprivado.free_area.exceptions.PrincipalPhotoNotFoundException;
import com.iprivado.free_area.mapper.FreeAreaMapper;
import com.iprivado.free_area.mapper.PrincipalPhotoMapper;
import com.iprivado.free_area.mapper.PublicContentMapper;
import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.models.entity.PrincipalPhoto;
import com.iprivado.free_area.models.entity.PublicContent;
import com.iprivado.free_area.repositories.FreeAreaRepository;
import com.iprivado.free_area.repositories.PrincipalPhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.iprivado.free_area.enums.DateConfiguration.TODAY;

@Service
public class FreeAreaServiceImpl implements FreeAreaService {

    private final FreeAreaRepository freeAreaRepository;

    private final PrincipalPhotoRepository principalPhotoRepository;

    private final FreeAreaMapper freeAreaMapper;

    private final PrincipalPhotoMapper principalPhotoMapper;

    private final PublicContentMapper publicContentMapper;

    public FreeAreaServiceImpl(FreeAreaRepository freeAreaRepository, UserClientRest userClientRest, PrincipalPhotoRepository principalPhotoRepository, FreeAreaMapper freeAreaMapper, PrincipalPhotoMapper principalPhotoMapper, PublicContentMapper publicContentMapper) {
        this.freeAreaRepository = freeAreaRepository;
        this.principalPhotoRepository = principalPhotoRepository;
        this.freeAreaMapper = freeAreaMapper;
        this.principalPhotoMapper = principalPhotoMapper;
        this.publicContentMapper = publicContentMapper;
    }

    @Override
    public List<FreeAreaDTO> findAll() {
        List<FreeArea> freeArea = freeAreaRepository.findAll();
        return freeArea.stream()
                .map(freeAreaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PrincipalPhotoDTO> getPrincipalPhoto(Long id) {
        FreeArea freeArea = freeAreaRepository.findById(id)
                .orElseThrow(() -> new FreeAreaNotFoundException("FreeArea with id " + id + " not found"));
        PrincipalPhotoDTO principalPhotoDTO = principalPhotoMapper.toDTO(freeArea.getPrincipalPhoto().get(0));
        return Optional.ofNullable(principalPhotoDTO);
    }

    public PrincipalPhoto editPrincipalPhoto(Long id, String principalPhotoUrl) {
        FreeArea freeArea = freeAreaRepository.findById(id)
                .orElseThrow(() -> new FreeAreaNotFoundException("FreeArea with id " + id + " not found"));
        PrincipalPhoto principalPhoto = freeArea.getPrincipalPhoto().stream()
                .findFirst()
                .orElseThrow(() -> new PrincipalPhotoNotFoundException("Principal photo not found for FreeArea with id " + id));
        principalPhoto.setUrl(principalPhotoUrl);
        principalPhoto.setUpdatedAt(TODAY.getValue());
        return principalPhotoRepository.save(principalPhoto);
    }

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
    public void delete(Long id) {
        Optional<FreeArea> freeArea = freeAreaRepository.findById(id);
        if (freeArea.isEmpty()) {
            throw new FreeAreaNotFoundException("Free Area was not found");
        }
        freeAreaRepository.logicDelete(id);
    }


}
