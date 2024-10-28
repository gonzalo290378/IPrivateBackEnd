package com.iprivado.free_area.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private Long age;

    private Long ageFrom;

    private Long ageTo;

    private String username;

    private String sex;

    private String email;

    private LocalDate birthdate;

    private String city;

    private String country;

    private LocalDate registerDate;

    private String description;

    private Boolean isEnabled;

    private String password;

}