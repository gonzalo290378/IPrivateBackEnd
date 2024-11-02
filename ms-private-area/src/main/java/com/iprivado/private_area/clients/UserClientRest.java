package com.iprivado.free_area.clients;

import org.springframework.cloud.openfeign.FeignClient;

//COMUNICACION CON ENTRE MSVC-CURSOS (POD) -> MSVC-USUARIOS (LOCAL) HACIENDO USO DE @FeignClient(name="msvc-usuarios", url = "host.docker.internal:8001")
//COMUNICACION CON ENTRE MSVC-CURSOS (POD) -> MSVC-USUARIOS (POD) HACIENDO USO DE @FeignClient(name="msvc-usuarios", url = "msvc-usuarios:8001")
@FeignClient(name = "ms-users", url = "${ms.users.url}") //COMUNICACION MEDIANTE VARIABLES DE AMBIENTE
public interface UserClientRest {

//    @PostMapping("/")
//    User save(@RequestBody User freeArea);

}
