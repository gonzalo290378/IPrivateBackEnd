package com.iprivado.apiext.controller;

import com.iprivado.apiext.model.entity.City;
import com.iprivado.apiext.model.entity.Country;
import com.iprivado.apiext.model.entity.State;
import com.iprivado.apiext.services.CityService;
import com.iprivado.apiext.services.CountryService;
import com.iprivado.apiext.services.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RequestMapping("/api/v1/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @GetMapping()
    public ResponseEntity<List<Country>> searchCountries(@RequestParam String name) {
        List<Country> countryList = countryService.searchCountriesByName(name);
        return ResponseEntity.ok(countryList);

    }


    @GetMapping("/statesByCountry")
    public ResponseEntity<List<State>> getStates(@RequestParam String name) {
        Country country = countryService.getIdByCountryname(name);
        List<State> stateList = stateService.getStatesByCountry(country.getId());
        return ResponseEntity.ok(stateList);
    }
}



