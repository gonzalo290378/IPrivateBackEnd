package com.ms_users.controllers;

import com.ms_users.dto.*;
import com.ms_users.exceptions.UserNotFoundException;
import com.ms_users.models.entity.User;
import com.ms_users.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UserController {

    @Value("${configuration.text}")
    private String text;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/authorized")
//    public Map<String, String> authorized(@RequestParam String code) {
//        log.info("Calling authorized with {}", code);
//        return Collections.singletonMap("code", code);
//    }

    @GetMapping("/authorized")
    public Map<String, String> authorized() {
        log.info("Calling authorized with {}");
        return Collections.emptyMap();
    }


    @GetMapping
    public ResponseEntity<Page<?>> findAll(Integer page, Integer size) {
        log.info("ms-users Calling findAll");
        return ResponseEntity.ok(userService.findAll(page, size));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        log.info("ms-users Calling findById with {}", id);
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        log.info("ms-users Calling findByUsername with {}", username);
        return ResponseEntity.ok(userService.findByUsername(username));
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

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody UserFormDTO userFormDTO, @PathVariable Long id) {
        log.info("ms-users Calling edit with {user}");
        User user = userService.findEntityById(id).orElseThrow(()-> new UserNotFoundException("User with id " + id + " not found"));
        return ResponseEntity.ok(userService.update(userFormDTO, user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("ms-users Calling delete with {id}");
        User user = userService.findEntityById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return ResponseEntity.ok(userService.delete(user.getId()));
    }

    @GetMapping("fetch-config")
    public ResponseEntity<?> fetchConfig(@Value("${server.port}")  String port){
        Map<String, String> json = new HashMap<>();
        json.put("port", port);
        json.put("text", text);
        return ResponseEntity.ok(json);
    }
}