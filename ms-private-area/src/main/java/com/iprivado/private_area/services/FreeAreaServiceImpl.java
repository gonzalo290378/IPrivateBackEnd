package com.iprivado.free_area.services;

import com.iprivado.free_area.clients.UserClientRest;
import com.iprivado.free_area.exceptions.FreeAreaNotFoundException;
import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.models.entity.PrincipalPhoto;
import com.iprivado.free_area.models.entity.PublicContent;
import com.iprivado.free_area.repositories.FreeAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FreeAreaServiceImpl implements FreeAreaService {

    @Autowired
    private FreeAreaRepository freeAreaRepository;

    @Autowired
    private UserClientRest userClientRest;

    public FreeAreaServiceImpl(FreeAreaRepository freeAreaRepository, UserClientRest userClientRest) {
        this.freeAreaRepository = freeAreaRepository;
        this.userClientRest = userClientRest;
    }

    @Override
    public List<FreeArea> findAll() {
        return freeAreaRepository.findAll();
    }

    public Optional<FreeArea> findById(Long id) {
        return Optional.ofNullable(freeAreaRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst().orElseThrow(() ->
                        new FreeAreaNotFoundException("id: " + id + " does not exist")));
    }


    @Transactional
    public FreeArea save(Boolean isEnabled) {
        FreeArea newFreeArea = new FreeArea().builder()
                .isEnabled(isEnabled)
                .build();
        return freeAreaRepository.save(newFreeArea);
    }


}
