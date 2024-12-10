package com.iprivado.apiext.controller;

import com.iprivado.apiext.model.entity.Country;
import com.iprivado.apiext.services.CityService;
import com.iprivado.apiext.services.CountryService;
import com.iprivado.apiext.services.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping()
    public List<Country> searchCities(@RequestParam String name) {
        return cityService.searchCitiesByName(name);
    }
}
