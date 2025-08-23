package com.iprivado.private_area.controllers;

import com.iprivado.private_area.dto.PrivateAreaDTO;
import com.iprivado.private_area.exceptions.PrivateAreaNotFoundException;
import com.iprivado.private_area.models.entity.PrivateArea;
import com.iprivado.private_area.services.PrivateAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/private-area")
public class PrivateAreaController {

    public PrivateAreaController(PrivateAreaService privateAreaService) {
        this.privateAreaService = privateAreaService;
    }

    private final PrivateAreaService privateAreaService;

    @Value("${freearea.internal-token}")
    private String SECRET_KEY;

    @GetMapping
    public ResponseEntity<List<PrivateAreaDTO>> findAll() {
        log.info("ms-private-area Calling findAll");
        return ResponseEntity.ok(privateAreaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrivateAreaDTO> findById(@PathVariable Long id) {
        log.info("ms-private-area Calling findById");
        return ResponseEntity.ok(privateAreaService.findById(id).orElseThrow(() -> new PrivateAreaNotFoundException("Private Area with id : " + id + " not found")));
    }


    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody Boolean isEnabled,
            @RequestHeader(value = "X-Internal-Token", required = false) String token) {

        if (!SECRET_KEY.equals(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
        }

        log.info("ms-free-area Calling save with new FreeArea object");
        PrivateArea saved = privateAreaService.save(isEnabled);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("ms-private-area Calling delete with {id}");
        privateAreaService.delete(id);
    }
}
