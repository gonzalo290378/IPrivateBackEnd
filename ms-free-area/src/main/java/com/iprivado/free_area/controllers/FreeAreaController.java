package com.iprivado.free_area.controllers;

import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.services.FreeAreaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/free-area")
public class FreeAreaController {

    @Autowired
    private FreeAreaService freeAreaService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody FreeArea freeArea) {
        log.info("Calling save with {}", freeArea);
        return ResponseEntity.ok(freeAreaService.save(freeArea));
    }


}
