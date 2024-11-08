package com.ms_users.clients;

import com.ms_users.models.FreeArea;
import com.ms_users.models.FreeAreaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//COMUNICACION CON ENTRE MSVC-USUARIOS (POD) -> MSVC-CURSOS (LOCAL) HACIENDO USO DE @FeignClient(name="msvc-cursos", url = "host.docker.internal:8002")
//COMUNICACION CON ENTRE MSVC-USUARIOS (POD) -> MSVC-CURSOS (POD) HACIENDO USO DE @FeignClient(name="msvc-cursos", url = "msvc-cursos:8002")
//@FeignClient(name = "ms-free-area", url = "${ms.free-area.url}") //COMUNICACION MEDIANTE VARIABLES DE AMBIENTE
@FeignClient(name = "ms-free-area", url = "localhost:8002") //COMUNICACION MEDIANTE VARIABLES DE AMBIENTE
public interface FreeAreaClientRest {

    @GetMapping("api/v1/free-area")
    List<FreeAreaDTO> findAll();

    @GetMapping("api/v1/free-area/{id}")
    FreeAreaDTO findById(@PathVariable Long id);

    @PostMapping("/api/v1/free-area")
    FreeAreaDTO save(@RequestBody Boolean isEnabled);

    @DeleteMapping("/api/v1/free-area/{id}")
    void delete(@PathVariable Long id);
}