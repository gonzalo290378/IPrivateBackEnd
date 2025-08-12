package com.iprivado.private_area.services;

import com.iprivado.private_area.clients.UserClientRest;
import com.iprivado.private_area.dto.PrivateAreaDTO;
import com.iprivado.private_area.exceptions.PrivateAreaNotFoundException;
import com.iprivado.private_area.mapper.PrivateAreaMapper;
import com.iprivado.private_area.models.entity.PrivateArea;
import com.iprivado.private_area.repositories.PrivateAreaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrivateAreaServiceImpl implements PrivateAreaService {

    private final PrivateAreaRepository privateAreaRepository;
    private final PrivateAreaMapper privateAreaMapper;
    private UserClientRest userClientRest;

    public PrivateAreaServiceImpl(PrivateAreaRepository privateAreaRepository, UserClientRest userClientRest, PrivateAreaMapper privateAreaMapper) {
        this.privateAreaRepository = privateAreaRepository;
        this.userClientRest = userClientRest;
        this.privateAreaMapper = privateAreaMapper;
    }

    @Override
    public List<PrivateAreaDTO> findAll() {
        List<PrivateArea> privateArea = privateAreaRepository.findAll();
        return privateArea.stream()
                .map(privateAreaMapper::toDTO)
                .collect(Collectors.toList());


    }

    public Optional<PrivateAreaDTO> findById(Long id) {
        return Optional.ofNullable(privateAreaRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .map(privateAreaMapper::toDTO)
                .findFirst().orElseThrow(() ->
                        new PrivateAreaNotFoundException("id: " + id + " does not exist")));
    }


    @Transactional
    public PrivateArea save(Boolean isEnabled) {
        PrivateArea privateArea = PrivateArea.builder()
                .isEnabled(isEnabled)
                .monthCostPrivateArea(BigDecimal.ZERO)
                .privateContent(new ArrayList<>())
                .build();
        privateAreaRepository.save(privateArea);
        return privateArea;
    }

    @Transactional
    public void delete(Long id) {
        Optional<PrivateArea> privateArea = privateAreaRepository.findById(id);
        if (privateArea.isEmpty()) {
            throw new PrivateAreaNotFoundException("Private Area was not found");
        }
        privateAreaRepository.logicDelete(id);
    }

}
