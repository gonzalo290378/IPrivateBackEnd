package com.ms_users.clients;

import com.ms_users.models.PrivateAreaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//COMUNICACION CON ENTRE MSVC-USUARIOS (POD) -> MSVC-CURSOS (LOCAL) HACIENDO USO DE @FeignClient(name="msvc-cursos", url = "host.docker.internal:8002")
//COMUNICACION CON ENTRE MSVC-USUARIOS (POD) -> MSVC-CURSOS (POD) HACIENDO USO DE @FeignClient(name="msvc-cursos", url = "msvc-cursos:8002")
@FeignClient(name = "ms-private-area", url = "localhost:8003") //COMUNICACION MEDIANTE VARIABLES DE AMBIENTE
public interface PrivateAreaClientRest {

    @GetMapping("api/v1/private-area")
    List<PrivateAreaDTO> findAll();

    @GetMapping("api/v1/private-area/{id}")
    PrivateAreaDTO findById(@PathVariable Long id);

    @PostMapping("/api/v1/private-area")
    PrivateAreaDTO save(@RequestBody Boolean isEnabled,
                        @RequestHeader("X-Internal-Token") String token);

    @DeleteMapping("/api/v1/private-area/{id}")
    void logicalDelete(@PathVariable Long id);
}