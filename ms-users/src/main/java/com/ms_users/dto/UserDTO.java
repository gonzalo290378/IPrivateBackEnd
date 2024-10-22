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
import java.time.Period;
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

    @JsonProperty("email")
    private String email;

    @JsonProperty("birthdate")
    private String birthdate;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @JsonProperty("registerDate")
    private String registerDate;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isEnabled")
    private Boolean isEnabled;

    @JsonProperty("password")
    private String password;

    public Long calculateAge(String birthdate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthdayFmt = LocalDate.parse(birthdate, fmt);
        LocalDate now = LocalDate.now();
        Period age = Period.between(birthdayFmt, now);
        return (long) age.getYears();
    }

}
