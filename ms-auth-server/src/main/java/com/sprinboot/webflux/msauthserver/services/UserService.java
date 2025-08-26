package com.sprinboot.webflux.msauthserver.services;

import com.sprinboot.webflux.msauthserver.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${users.service.token}")
    private String SECRET_KEY_USERS;

    public User findByEmail(String email) {
        HashMap<String, String> uriPathVariable = new HashMap<>();
        uriPathVariable.put("email", email);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Service", SECRET_KEY_USERS);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<User> response = restTemplate.exchange(
                "http://localhost:8090/ms-users/email/{email}",
                HttpMethod.GET,
                entity,
                User.class,
                uriPathVariable
        );

        return response.getBody();

    }
}