package com.iprivado.messages.clients;

import com.iprivado.messages.dto.PrincipalPhotoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@FeignClient(name = "ms-free-area", url = "${ms.freearea.url}")
public interface FreeAreaClientRest {

    @GetMapping("/api/v1/free-area/{id}/principal-photo")
    Optional<PrincipalPhotoDTO> getPrincipalPhoto(
            @PathVariable Long id,
            @RequestHeader("X-Internal-Token") String token
    );
}
