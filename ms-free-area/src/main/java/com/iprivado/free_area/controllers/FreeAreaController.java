package com.iprivado.free_area.controllers;

import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.dto.PublicContentDTO;
import com.iprivado.free_area.exceptions.FreeAreaNotFoundException;
import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.services.FreeAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/free-area")
public class FreeAreaController {

    public FreeAreaController(FreeAreaService freeAreaService) {
        this.freeAreaService = freeAreaService;
    }

    private final FreeAreaService freeAreaService;

    @Value("${freearea.internal-token}")
    private String SECRET_KEY;

    @GetMapping
    public ResponseEntity<List<FreeAreaDTO>> findAll() {
        log.info("ms-free-area Calling findAll");
        return ResponseEntity.ok(freeAreaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FreeAreaDTO> findById(@PathVariable Long id) {
        log.info("ms-free-area Calling findById");
        return ResponseEntity.ok(freeAreaService.findById(id).orElseThrow(() -> new FreeAreaNotFoundException("FreeArea with id " + id + " not found")));
    }

    @GetMapping("/{id}/principal-photo")
    public ResponseEntity<?> findPrincipalPhoto(@PathVariable Long id) {
        log.info("ms-free-area Calling findPrincipalPhoto for FreeArea id {}", id);
        return ResponseEntity.ok(freeAreaService.getPrincipalPhoto(id));
    }

    @GetMapping("/{id}/public-content")
    public ResponseEntity<?> findPublicContent(@PathVariable Long id, @RequestParam() Long lastId, @RequestParam(defaultValue = "10") int limit) {
        log.info("ms-free-area Calling getPublicContent for FreeArea id {}", id);
        return ResponseEntity.ok(freeAreaService.getPublicContent(id, lastId, limit));
    }

    //SECURIZAR
    @PutMapping("/{id}/public-content/{idContent}")
    public ResponseEntity<?> editPublicContent(@PathVariable Long id, @PathVariable Long idContent, @RequestBody PublicContentDTO publicContentDTO) {
        log.info("ms-free-area Calling editPublicContent for freeAreaId {} and contentId {}", id, idContent);
        return ResponseEntity.ok(freeAreaService.updatePublicContent(id, idContent, publicContentDTO));
    }

    @PostMapping("/principal-photo/upload")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> uploadPrincipalPhoto(@RequestParam("file") MultipartFile file, @RequestParam("idFreeArea") Long idFreeArea) {
        log.info("ms-free-area Calling uploadPrincipalPhoto for FreeArea id {}", idFreeArea);
        return ResponseEntity.ok(freeAreaService.addPrincipalPhoto(file, idFreeArea));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Boolean isEnabled, @RequestHeader(value = "X-Internal-Token") String token) {
        if (!SECRET_KEY.equals(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
        }

        log.info("ms-free-area Calling save with new FreeArea object");
        FreeArea saved = freeAreaService.save(isEnabled);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/public-content")
    public ResponseEntity<?> savePublicContent(@PathVariable Long id, @RequestPart("description") String description,
                                               @RequestPart("files") List<MultipartFile> files) {

        log.info("ms-free-area Calling savePublicContent for FreeArea id {}", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(freeAreaService.addPublicContent(id, description, files));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("ms-free-area Calling delete with {id}");
        freeAreaService.delete(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/reactivate")
    public void reactivateUser(@PathVariable Long id) {
        log.info("ms-free-area Calling reactivateUser with {id}");
        freeAreaService.reactivateUser(id);
    }

    //SECURIZAR
    @DeleteMapping("/{id}/public-content/{idContent}")
    public void deletePublicContent(@PathVariable Long id, @PathVariable Long idContent) {
        log.info("ms-free-area Calling deletePublicContent for FreeArea id {}", id);
        freeAreaService.deletePublicContent(id, idContent);
    }

}
