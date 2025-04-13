package com.sprinboot.webflux.ms_oauth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

    private Long age;

    private String sex;
    private Boolean isEnabled;

    private LocalDate birthdate;

    private LocalDate registerDate;

    private String description;

    private List<Role> roles;

    private String email;

    private Long idFreeArea;

    private Long idPrivateArea;

    private Preference preference;

    private Country country;

    private City city;

    private Boolean admin = false;
    
}
