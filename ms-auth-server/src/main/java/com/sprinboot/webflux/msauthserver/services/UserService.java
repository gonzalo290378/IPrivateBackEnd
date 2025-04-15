package com.sprinboot.webflux.msauthserver.services;

import com.sprinboot.webflux.msauthserver.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public User findByEmail(String email) {
        HashMap<String, String> uriPathVariable = new HashMap<>();
        uriPathVariable.put("email", email);
        return restTemplate.getForObject("http://localhost:8090/ms-users/email/{email}", User.class, uriPathVariable);
    }
}