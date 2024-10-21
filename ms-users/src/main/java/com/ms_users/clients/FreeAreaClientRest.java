package com.ms_users.clients;

import com.ms_users.models.entity.FreeArea;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//COMUNICACION CON ENTRE MSVC-USUARIOS (POD) -> MSVC-CURSOS (LOCAL) HACIENDO USO DE @FeignClient(name="msvc-cursos", url = "host.docker.internal:8002")
//COMUNICACION CON ENTRE MSVC-USUARIOS (POD) -> MSVC-CURSOS (POD) HACIENDO USO DE @FeignClient(name="msvc-cursos", url = "msvc-cursos:8002")
@FeignClient(name = "ms-free-area", url = "${ms.free-area.url}") //COMUNICACION MEDIANTE VARIABLES DE AMBIENTE

public interface FreeAreaClientRest {

    @PostMapping("/")
    FreeArea save(@RequestBody FreeArea freeArea);

    @DeleteMapping("/delete-free-area/{id}")
    void delete(@PathVariable Long id);
}