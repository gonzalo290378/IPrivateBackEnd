package com.sprinboot.webflux.msauthserver.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserProfileDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    private List<Role> roles = new ArrayList<>();

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    private String password;
}
