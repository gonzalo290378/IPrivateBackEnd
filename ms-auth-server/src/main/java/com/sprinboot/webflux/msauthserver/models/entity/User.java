package com.sprinboot.webflux.msauthserver.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private List<Role> roles = new ArrayList<>();

    private String username;

    private Long age;

    private String sex;

    private String email;

    private LocalDate birthdate;

    private LocalDate registerDate;

    private String description;

    private Boolean isEnabled;

    private String password;

    private Long idFreeArea;

    private Long idPrivateArea;

    private Preference preference;

    private Country country;

    private City city;

    private Boolean admin = false;

}