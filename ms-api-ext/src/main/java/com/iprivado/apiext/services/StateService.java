package com.iprivado.apiext.services;

import com.iprivado.apiext.model.entity.State;
import com.iprivado.apiext.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class StateService {

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<State> getStatesByCountry(String idCountry) {
        return stateRepository.findByIdCountry(parseInt(idCountry));
    }

    public State getIdByStatenameAndIdCountry(String name, int idCountry) {
        return stateRepository.findByNameAndIdCountry(name, idCountry);
    }

    public List<State> searchStates(String name, List<State> states) {
        return states.stream()
                .filter(state -> state.getName() != null && state.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

}
