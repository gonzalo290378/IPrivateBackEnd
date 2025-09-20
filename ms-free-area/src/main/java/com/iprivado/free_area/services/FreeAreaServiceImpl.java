package com.iprivado.free_area.services;

import com.iprivado.free_area.clients.UserClientRest;
import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.dto.PrincipalPhotoDTO;
import com.iprivado.free_area.dto.PublicContentDTO;
import com.iprivado.free_area.exceptions.FreeAreaNotFoundException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.iprivado.free_area.enums.DateConfiguration.TODAY;

@Service
@Slf4j
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
    public Optional<FreeAreaDTO> findById(Long id) {
        return Optional.ofNullable(freeAreaRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .map(freeAreaMapper::toDTO)
                .findFirst().orElseThrow(() ->
                        new FreeAreaNotFoundException("FreeArea with id " + id + " not found")));
    }

    @Transactional(readOnly = true)
    public Optional<PrincipalPhotoDTO> getPrincipalPhoto(Long id) {
        FreeArea freeArea = freeAreaRepository.findById(id)
                .orElseThrow(() -> new FreeAreaNotFoundException("FreeArea with id " + id + " not found"));

        return freeArea.getPrincipalPhoto().stream()
                .filter(principalPhoto -> principalPhoto.getId() != null)
                .findFirst()
                .map(principalPhotoMapper::toDTO);
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


    @Override
    public PrincipalPhoto addPrincipalPhoto(MultipartFile file, Long idFreeArea) {
        try {
            File uploadDir = new File("C:/Users/Gonzalo/Desktop/Programacion-Verdadero/IPrivate/Backend/uploads/principal-photo");
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadDir.toPath().resolve(fileName);
            file.transferTo(filePath.toFile());

            Optional<PrincipalPhoto> existing = principalPhotoRepository.findByIdFreeArea(idFreeArea);

            PrincipalPhoto principalPhoto;
            if (existing.isPresent()) {
                principalPhoto = existing.get();
                String oldUrl = principalPhoto.getUrl();
                if (oldUrl != null) {
                    Path oldPath = Paths.get("C:/Users/Gonzalo/Desktop/Programacion-Verdadero/IPrivate/Backend" + oldUrl);
                    File oldFile = oldPath.toFile();
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }
                principalPhoto.setUrl("/uploads/principal-photo/" + fileName);
                principalPhoto.setUpdatedAt(LocalDate.now());
            } else {
                PrincipalPhotoDTO principalPhotoDTO = new PrincipalPhotoDTO();
                principalPhotoDTO.setIdFreeArea(idFreeArea);
                principalPhotoDTO.setIsEnabled(true);
                principalPhotoDTO.setCreatedAt(LocalDate.now());
                principalPhotoDTO.setUpdatedAt(LocalDate.now());
                principalPhotoDTO.setUrl("/uploads/principal-photo/" + fileName);
                principalPhoto = principalPhotoMapper.toModel(principalPhotoDTO);
            }

            return principalPhotoRepository.save(principalPhoto);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar PrincipalPhoto", e);
        }
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
    public List<PublicContentDTO> addPublicContent(Long id, String description, List<MultipartFile> files) {
        try {
            FreeArea freeArea = freeAreaRepository.findById(id)
                    .orElseThrow(() -> new FreeAreaNotFoundException("FreeArea with id " + id + " not found"));
            List<PublicContentDTO> listPublicContentDTO = new ArrayList<>();

            for (MultipartFile file : files) {
                File uploadDir = new File("C:/Users/Gonzalo/Desktop/Programacion-Verdadero/IPrivate/Backend/uploads/public-content");
                if (!uploadDir.exists()) uploadDir.mkdirs();
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = uploadDir.toPath().resolve(fileName);
                file.transferTo(filePath.toFile());

                PublicContentDTO publicContentDTO = PublicContentDTO.builder()
                        .description(description)
                        .idFreeArea(id)
                        .isEnabled(true)
                        .contentUrl("/uploads/public-content/" + fileName)
                        .likesCount(0L)
                        .createdAt(LocalDate.now())
                        .updatedAt(LocalDate.now())
                        .build();

                PublicContent publicContent = publicContentMapper.toModel(publicContentDTO);
                PublicContent newPublicContent = publicContentRepository.save(publicContent);
                freeArea.getPublicContent().add(newPublicContent);
                listPublicContentDTO.add(publicContentDTO);
            }

            freeAreaRepository.save(freeArea);
            return listPublicContentDTO;

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar PublicContent", e);
        }
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
    private void deletePrincipalPhoto(Long idPrincipalPhoto) {
        principalPhotoRepository.logicalDelete(idPrincipalPhoto);
    }

    @Transactional
    public void deletePublicContent(Long id, Long idContent) {
        publicContentRepository.logicalDelete(id, idContent);
    }

}
