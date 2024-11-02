package com.ms_users.controllers;

import com.ms_users.dto.*;
import com.ms_users.models.entity.User;
import com.ms_users.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        log.info("ms-users Calling findAll");
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        log.info("ms-users Calling findById with {}", id);
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        log.info("ms-users Calling findByEmail with {email}");
        Optional<UserDTO> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<FilterDTO>> filter(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "ageFrom", required = true) Long ageFrom,
            @RequestParam(name = "ageTo", required = true) Long ageTo,
            @RequestParam(name = "sexPreference", required = true) String sexPreference,
            @RequestParam(name = "city", required = true) String city,
            @RequestParam(name = "country", required = true) String country,
            @RequestParam(name = "isEnabled", required = true) Boolean isEnabled) {

        PreferenceDTO preferenceDTO = PreferenceDTO.builder()
                .ageFrom(ageFrom)
                .ageTo(ageTo)
                .sexPreference(sexPreference)
                .build();

        CountryDTO countryDTO = CountryDTO.builder()
                .country(country)
                .build();

        CityDTO cityDTO = CityDTO.builder()
                .city(city)
                .build();

        FilterDTO filterDTO = FilterDTO.builder()
                .preferenceDTO(preferenceDTO)
                .countryDTO(countryDTO)
                .cityDTO(cityDTO)
                .isEnabled(isEnabled)
                .build();
        log.info("ms-users Calling filter with {}", filterDTO);
        return ResponseEntity.ok(userService.filter(filterDTO, page, size));
    }


    @PostMapping()
    public ResponseEntity<?> save(@RequestBody @Valid UserFormDTO userFormDTO) {
        log.info("ms-users Calling save with {userFormDTO}");
        return ResponseEntity.ok(userService.save(userFormDTO));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> edit(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
//        log.info("ms-users Calling edit with {user}");
//
//        if (result.hasErrors()) {
//            return userService.validate(result);
//        }
//
//        Optional<UserDTO> o = userService.findById(id);
//        if (o.isPresent()) {
//            UserDTO usuarioDb = o.get();
//
//            if (userService.hasInvalidFields(user, usuarioDb)) {
//                return ResponseEntity.badRequest()
//                        .body(Collections.singletonMap("Message Application", "Some data cannot be empty"));
//            }
//            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
//        }
//        return ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("ms-users Calling delete with {id}");
        Optional<UserDTO> o = userService.findById(id);
        if (o.isPresent()) {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
