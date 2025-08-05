package com.iprivado.apiext.controller;

import com.iprivado.apiext.model.entity.Country;
import com.iprivado.apiext.model.entity.State;
import com.iprivado.apiext.services.CityService;
import com.iprivado.apiext.services.CountryService;
import com.iprivado.apiext.services.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RequestMapping("/api/v1/countries")
public class CountryController {

    public CountryController(CountryService countryService, StateService stateService, CityService cityService) {
        this.countryService = countryService;
        this.stateService = stateService;
        this.cityService = cityService;
    }

    private final CountryService countryService;

    private final StateService stateService;

    private final CityService cityService;

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



