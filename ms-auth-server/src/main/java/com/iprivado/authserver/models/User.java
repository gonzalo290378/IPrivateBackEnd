package com.iprivado.authserver.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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