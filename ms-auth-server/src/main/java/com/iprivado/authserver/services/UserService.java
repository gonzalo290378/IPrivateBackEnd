package com.iprivado.authserver.services;

import com.iprivado.authserver.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public User findByUsername(String username) {
        HashMap<String, String> uriPathVariable = new HashMap<>();
        uriPathVariable.put("username", username);
        return restTemplate.getForObject("http://localhost:8090/ms-users/api/v1/users/username/{username}", User.class, uriPathVariable);
    }
}
