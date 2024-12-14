package com.iprivado.apiext.controller;

import com.iprivado.apiext.dto.CitySearchRequest;
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

import static java.lang.Integer.parseInt;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RequestMapping("/api/v1/cities")
public class CityController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @GetMapping("/citiesByStates")
    public ResponseEntity<List<City>> searchCitiesByStates(@RequestParam String name, @RequestParam String country) {
        Country countrySelected = countryService.getIdByCountryname(country);
        State state = stateService.getIdByStatenameAndIdCountry(name, parseInt( countrySelected.getId()));
        List<City> result = cityService.searchCitiesByStates(state.getId());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/search")
    public ResponseEntity<List<City>> searchCities(@RequestBody CitySearchRequest request) {
        String name = request.getName();
        List<City> cities = request.getCities();
        List<City> result = cityService.searchCities(name, cities);
        return ResponseEntity.ok(result);
    }
}
