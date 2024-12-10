package com.iprivado.apiext.services;

import com.iprivado.apiext.model.entity.City;
import com.iprivado.apiext.model.entity.Country;
import com.iprivado.apiext.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> getCitiesByStates(String stateId) {
        return cityRepository.findByIdState(parseInt(stateId));
    }

    public List<Country> searchCitiesByName(String name) {
        return cityRepository.findByNameContainingIgnoreCase(name);
    }

}
