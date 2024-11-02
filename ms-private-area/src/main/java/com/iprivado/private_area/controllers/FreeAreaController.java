package com.iprivado.free_area.controllers;

import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.services.FreeAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/free-area")
public class FreeAreaController {

    @Autowired
    private FreeAreaService freeAreaService;

    @GetMapping
    public ResponseEntity<List<FreeArea>> findAll() {
        log.info("ms-free-area Calling findAll");
        return ResponseEntity.ok(freeAreaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FreeArea>findById(@PathVariable Long id) {
        log.info("ms-free-area Calling findById");
        return ResponseEntity.ok(freeAreaService.findById(id).get());
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody Boolean isEnabled) {
        log.info("ms-free-area Calling save with {new FreeArea object}");
        return ResponseEntity.ok(freeAreaService.save(isEnabled));
    }


}
