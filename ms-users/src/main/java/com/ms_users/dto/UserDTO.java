package com.ms_users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms_users.models.FreeAreaDTO;
import com.ms_users.models.PrivateArea;
import com.ms_users.models.PrivateAreaDTO;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @Size(min = 5, message = "Username should have at least 5 characters")
    @Size(max = 10, message = "Username should not have more than 10 characters")
    @NotEmpty(message = "Username can not be empty")
    @JsonProperty("username")
    private String username;

    @Min(value = 18, message = "Age from must be more than 18")
    @Max(value = 90, message = "Age to must be less than 90")
    @JsonProperty("age")
    private Long age;

    @Pattern(regexp = "^[FM]$", message = "Sex preference must be 'F' or 'M'")
    @NotEmpty(message = "Sex preference must be 'F' or 'M'")
    @JsonProperty("sex")
    private String sex;

    @Email(message = "Please provide a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    @JsonProperty("email")
    private String email;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("birthdate")
    private LocalDate birthdate;

    @JsonProperty("registerDate")
    private LocalDate registerDate;

    @Size(min = 10, message = "The description must have more than 10 characters")
    @Size(max = 140, message = "The description must not exceed 144 characters")
    @NotEmpty(message = "Description can not be empty")
    @JsonProperty("description")
    private String description;

    @JsonProperty("isEnabled")
    private Boolean isEnabled;

    @Size(max = 12, message = "Password must have less than 12 characters")
    @Size(max = 6, message = "Password must not exceed 12 characters")
    @NotEmpty(message = "Password can not be empty")
    @JsonProperty("password")
    private String password;

    @JsonProperty("idFreeArea")
    private Long idFreeArea;

    @JsonProperty("idPrivateArea")
    private Long idPrivateArea;

    @JsonProperty("freeAreaDTO")
    private FreeAreaDTO freeAreaDTO;

    @JsonProperty("privateAreaDTO")
    private PrivateAreaDTO privateAreaDTO;

    @JsonProperty("preferenceDTO")
    private PreferenceDTO preferenceDTO;

    @JsonProperty("countryDTO")
    private CountryDTO countryDTO;

    @JsonProperty("cityDTO")
    private CityDTO cityDTO;

    public Long getAge() {
        LocalDate today = LocalDate.now();
        long age = Period.between(birthdate, today).getYears();

        if (birthdate.getDayOfMonth() == today.getDayOfMonth() && birthdate.getMonth() == today.getMonth()) {
            return age + 1;
        }

        return age;
    }

}
