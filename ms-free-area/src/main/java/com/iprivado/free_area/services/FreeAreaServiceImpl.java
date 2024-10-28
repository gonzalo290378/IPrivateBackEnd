package com.iprivado.free_area.services;

import com.iprivado.free_area.clients.UserClientRest;
import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.repositories.FreeAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FreeAreaServiceImpl implements FreeAreaService {

    @Autowired
    private FreeAreaRepository freeAreaRepository;

    @Autowired
    private UserClientRest client;

    public FreeAreaServiceImpl(FreeAreaRepository freeAreaRepository, UserClientRest client){
        this.freeAreaRepository = freeAreaRepository;
        this.client = client;
    }

    @Transactional
    public FreeArea save(FreeArea freeArea) {

        FreeArea newUser = new FreeArea().builder()

                .build();
        return freeAreaRepository.save(newUser);
    }


}
