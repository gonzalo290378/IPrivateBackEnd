package com.iprivado.apiext.services;

import com.iprivado.apiext.model.entity.City;
import com.iprivado.apiext.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> searchCitiesByStates(String idState) {
        return cityRepository.findByIdState(Integer.parseInt(idState));

    }

    public List<City> searchCities(String name, List<City> cities) {
        return cities.stream()
                .filter(city -> city.getName() != null && city.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

}
