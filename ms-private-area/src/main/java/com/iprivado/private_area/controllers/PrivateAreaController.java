package com.iprivado.private_area.controllers;

import com.iprivado.private_area.dto.PrivateAreaDTO;
import com.iprivado.private_area.services.PrivateAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/private-area")
public class PrivateAreaController {

    @Autowired
    private PrivateAreaService privateAreaService;

    @GetMapping
    public ResponseEntity<List<PrivateAreaDTO>> findAll() {
        log.info("ms-private-area Calling findAll");
        return ResponseEntity.ok(privateAreaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrivateAreaDTO> findById(@PathVariable Long id) {
        log.info("ms-private-area Calling findById");
        return ResponseEntity.ok(privateAreaService.findById(id).get());
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody Boolean isEnabled) {
        log.info("ms-private-area Calling save with {new FreeArea object}");
        return ResponseEntity.ok(privateAreaService.save(isEnabled));
    }


}
