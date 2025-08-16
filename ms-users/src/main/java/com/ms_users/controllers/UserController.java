package com.ms_users.controllers;

import com.ms_users.dto.*;
import com.ms_users.exceptions.UserNotFoundException;
import com.ms_users.models.entity.User;
import com.ms_users.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class UserController {

    @Value("${configuration.text}")
    private String text;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/authorized")
    public Map<String, String> authorized() {
        log.info("Calling authorized");
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
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        log.info("ms-users Calling findByUsername with {}", username);
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("Username was not found"));
    }

    @GetMapping("/check-availability-username/{username}")
    public ResponseEntity<Map<String, Boolean>> checkUsernameAvailability(@PathVariable String username) {
        log.info("ms-users Checking username availability for {}", username);
        boolean isAvailable = !userService.existsByUsername(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
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

    @GetMapping("/check-availability-email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmailAvailability(@PathVariable String email) {
        log.info("ms-users Checking email availability for {}", email);
        boolean isAvailable = !userService.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<FilterDTO>> filter(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "ageFrom") Long ageFrom,
            @RequestParam(name = "ageTo") Long ageTo,
            @RequestParam(name = "sexPreference") String sexPreference,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "country") String country,
            @RequestParam(name = "state") String state,
            @RequestParam(name = "isEnabled") Boolean isEnabled) {

        PreferenceDTO preferenceDTO = PreferenceDTO.builder()
                .ageFrom(ageFrom)
                .ageTo(ageTo)
                .sexPreference(sexPreference)
                .build();

        CountryDTO countryDTO = CountryDTO.builder()
                .country(country)
                .build();

        StateDTO stateDTO = StateDTO.builder()
                .state(state)
                .build();

        CityDTO cityDTO = CityDTO.builder()
                .city(city)
                .build();

        FilterDTO filterDTO = FilterDTO.builder()
                .preferenceDTO(preferenceDTO)
                .countryDTO(countryDTO)
                .stateDTO(stateDTO)
                .cityDTO(cityDTO)
                .isEnabled(isEnabled)
                .build();
        log.info("ms-users Calling filter with {}", filterDTO);
        return ResponseEntity.ok(userService.filter(filterDTO, page, size));
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody @Valid UserFormDTO userFormDTO) {
        log.info("ms-users Calling save with {userFormDTO}");
        return ResponseEntity.ok(userService.save(userFormDTO));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/edit/{username}")
    public ResponseEntity<?> edit(@Valid @RequestBody UserFormDTO userFormDTO, @PathVariable String username) {
        log.info("ms-users Calling edit with {user}");
        String authenticatedUsername = userService.getAuthenticatedUsername();
        if (!username.equals(authenticatedUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized to edit this user");
        }
        User user = userService.findEntityByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found"));
        return ResponseEntity.ok(userService.update(userFormDTO, user));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("ms-users Calling delete with {id}");
        User user = userService.findEntityById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return ResponseEntity.ok(userService.delete(user.getId()));
    }

    @GetMapping("fetch-config")
    public ResponseEntity<?> fetchConfig(@Value("${server.port}") String port) {
        Map<String, String> json = new HashMap<>();
        json.put("port", port);
        json.put("text", text);
        return ResponseEntity.ok(json);
    }
}