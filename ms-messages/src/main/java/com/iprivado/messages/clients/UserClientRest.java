package com.iprivado.messages.clients;

import com.iprivado.messages.dto.UserSummaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-users", url = "${ms.users.url}")
public interface UserClientRest {

    @GetMapping("/username")
    ResponseEntity<UserSummaryDTO> findByUsername(@RequestParam String username);
}