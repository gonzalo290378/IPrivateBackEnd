package com.iprivado.apiext.services;

import com.iprivado.apiext.model.entity.Country;
import com.iprivado.apiext.model.entity.State;
import com.iprivado.apiext.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public List<State> getStatesByCountry(String idCountry) {
        return stateRepository.findByIdCountry(parseInt(idCountry));
    }

    public List<State> searchStatesByName(String name) {
        return stateRepository.findByNameContainingIgnoreCase(name);
    }
}
