package com.iprivado.ms_consumer.clients;

import com.iprivado.ms_consumer.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//COMUNICACION CON ENTRE MSVC-USUARIOS (POD) -> MSVC-CURSOS (LOCAL) HACIENDO USO DE @FeignClient(name="msvc-cursos", url = "host.docker.internal:8002")
//COMUNICACION CON ENTRE MSVC-USUARIOS (POD) -> MSVC-CURSOS (POD) HACIENDO USO DE @FeignClient(name="msvc-cursos", url = "msvc-cursos:8002")

//@FeignClient(name = "ms-free-area", url = "${ms.free-area.url}") //COMUNICACION MEDIANTE VARIABLES DE AMBIENTE
@FeignClient(name = "ms-users", url = "http://localhost:8001")
public interface UserClientRest {

    @GetMapping("api/v1/users/{id}")
    User getUserById(@PathVariable("id") Long id);

}