package com.sprinboot.webflux.msauthserver.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class User implements Serializable {

    @JsonProperty("id")
    private Long id;

    private List<Role> roles = new ArrayList<>();

    @JsonProperty("username")
    private String username;

    @JsonProperty("age")
    private Long age;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("email")
    private String email;

    @JsonProperty("birthdate")
    private LocalDate birthdate;

    @JsonProperty("registerDate")
    private LocalDate registerDate;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isEnabled")
    private Boolean isEnabled;

    private String password;

    @JsonProperty("idFreeArea")
    private Long idFreeArea;

    @JsonProperty("idPrivateArea")
    private Long idPrivateArea;

    @JsonProperty("preferenceDTO")
    private PreferenceDTO preferenceDTO;

    @JsonProperty("countryDTO")
    private CountryDTO countryDTO;

    @JsonProperty("cityDTO")
    private CityDTO cityDTO;

}
