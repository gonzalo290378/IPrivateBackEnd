package com.iprivado.private_area.controllers;

import com.iprivado.private_area.dto.PrivateAreaDTO;
import com.iprivado.private_area.exceptions.PrivateAreaNotFoundException;
import com.iprivado.private_area.services.PrivateAreaService;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<?> save(@RequestBody Boolean isEnabled) {
        log.info("ms-private-area Calling save with {new FreeArea object}");
        return ResponseEntity.ok(privateAreaService.save(isEnabled));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("ms-private-area Calling delete with {id}");
        privateAreaService.delete(id);
    }


}
