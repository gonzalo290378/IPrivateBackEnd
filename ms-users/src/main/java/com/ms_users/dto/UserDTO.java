package com.ms_users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("freeAreaUser")
    private FreeAreaUserDTO freeAreaUser;

    @JsonProperty("privateAreaUser")
    private PrivateAreaUserDTO privateAreaUser;

    @JsonProperty("preferenceDTO")
    private PreferenceDTO preferenceDTO;

    @JsonProperty("countryDTO")
    private CountryDTO countryDTO;

    @JsonProperty("cityDTO")
    private CityDTO cityDTO;

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

    @JsonProperty("password")
    private String password;

    public Long getAge() {
        LocalDate today = LocalDate.now();

        if (birthdate.getDayOfMonth() == today.getDayOfMonth() && birthdate.getMonth() == today.getMonth()) {
            return this.age + 1;
        }
        return age;
    }

}
