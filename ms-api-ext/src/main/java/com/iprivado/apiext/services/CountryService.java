package com.iprivado.apiext.services;

import com.iprivado.apiext.model.entity.Country;
import com.iprivado.apiext.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> searchCountriesByName(String name) {
        return countryRepository.findByNameContainingIgnoreCase(name);
    }

    public Country getIdByCountryname(String name) {
        return countryRepository.findByName(name);
    }
}
