package com.ms_users.clients;

import com.ms_users.models.PrivateArea;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//COMUNICACION CON ENTRE MSVC-USUARIOS (POD) -> MSVC-CURSOS (LOCAL) HACIENDO USO DE @FeignClient(name="msvc-cursos", url = "host.docker.internal:8002")
//COMUNICACION CON ENTRE MSVC-USUARIOS (POD) -> MSVC-CURSOS (POD) HACIENDO USO DE @FeignClient(name="msvc-cursos", url = "msvc-cursos:8002")
@FeignClient(name = "ms-private-area", url = "localhost:8003") //COMUNICACION MEDIANTE VARIABLES DE AMBIENTE
public interface PrivateAreaClientRest {

    @GetMapping("api/v1/private-area")
    List<PrivateArea> findAll();

    @PostMapping("/api/v1/private-area")
    PrivateArea save(@RequestBody Boolean isEnabled);

    @DeleteMapping("/delete-free-area/{id}")
    void delete(@PathVariable Long id);
}