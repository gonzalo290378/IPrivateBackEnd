package com.ms_users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms_users.models.FreeAreaUser;
import com.ms_users.models.PrivateAreaUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("freeAreaUser")
    private FreeAreaUser freeAreaUser;

    @JsonProperty("privateAreaUser")
    private PrivateAreaUser privateAreaUser;

    @JsonProperty("username")
    private String username;

    @JsonProperty("age")
    private Long age;

    @JsonProperty("ageFrom")
    private Long ageFrom;

    @JsonProperty("ageTo")
    private Long ageTo;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("email")
    private String email;

    @JsonProperty("birthdate")
    private LocalDate birthdate;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

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
