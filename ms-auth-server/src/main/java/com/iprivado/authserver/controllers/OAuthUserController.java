package com.iprivado.authserver.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("")
@Slf4j
public class OAuthUserController {

    //login: http://127.0.0.1:8090/ms-users/oauth2/authorization/gayeway-app

    @GetMapping("/authorized")
    public Map<String, String> authorized(@RequestParam String code) {
        log.info("Calling authorized with {}", code);
        return Collections.singletonMap("code", code);
    }

}