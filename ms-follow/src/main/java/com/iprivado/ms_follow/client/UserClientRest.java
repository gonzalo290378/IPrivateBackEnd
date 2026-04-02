package com.iprivado.ms_follow.client;

import com.iprivado.ms_follow.dto.UserSummaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-users", url = "${ms.users.url}")
public interface UserClientRest {

    @GetMapping("/id/{id}")
    UserSummaryDTO findById(@PathVariable Long id);

    @GetMapping("/username")
    UserSummaryDTO findByUsername(@RequestParam String username);
}
