package com.iprivado.free_area.controllers;

import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.exceptions.FreeAreaNotFoundException;
import com.iprivado.free_area.services.FreeAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/free-area")
public class FreeAreaController {

    public FreeAreaController(FreeAreaService freeAreaService){
        this.freeAreaService = freeAreaService;
    }

    private final FreeAreaService freeAreaService;

    @GetMapping
    public ResponseEntity<List<FreeAreaDTO>> findAll() {
        log.info("ms-free-area Calling findAll");
        return ResponseEntity.ok(freeAreaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FreeAreaDTO> findById(@PathVariable Long id) {
        log.info("ms-free-area Calling findById");
        return ResponseEntity.ok(freeAreaService.findById(id).orElseThrow(()-> new FreeAreaNotFoundException("FreeArea with id " + id + " not found")));
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody Boolean isEnabled) {
        log.info("ms-free-area Calling save with {new FreeArea object}");
        return ResponseEntity.ok(freeAreaService.save(isEnabled));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("ms-free-area Calling delete with {id}");
        freeAreaService.delete(id);
    }

}
