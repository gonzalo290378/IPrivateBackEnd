package com.ms_users.controllers;

import com.ms_users.dto.UserDTO;
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
        log.info("Calling findAll");
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        log.info("Calling findById with {}", id);
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        log.info("Calling findByEmail with {email}");
        Optional<UserDTO> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        log.info("Calling findByUsername with {username}");
        Optional<UserDTO> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<UserDTO>> filter(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "username", required = false) String username,
            //TODO AGREGAR FILTRO POR RANGO DE EDAD
            @RequestParam(name = "birthdate", required = false) String birthdate,
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "isEnabled", required = false) Boolean isEnabled) {

        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .birthdate(birthdate)
                .city(city)
                .country(country)
                .isEnabled(isEnabled)
                .build();
        log.info("Calling filter with {}", userDTO);
        return ResponseEntity.ok(userService.filter(userDTO, page, size));
    }


    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody User user) {
        log.info("Calling save with {}", user);
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
        log.info("Calling edit with {user}");

        if (result.hasErrors()) {
            return userService.validate(result);
        }

        Optional<UserDTO> o = userService.findById(id);
        if (o.isPresent()) {
            UserDTO usuarioDb = o.get();

            if (userService.hasInvalidFields(user, usuarioDb)) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("Message Application", "Some data cannot be empty"));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<UserDTO> o = userService.findById(id);
        if (o.isPresent()) {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
