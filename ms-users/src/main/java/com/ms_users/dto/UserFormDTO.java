package com.ms_users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms_users.models.entity.Role;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.Period;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFormDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("roles")
    private List<Role> roles = new ArrayList<>();

    @Size(min = 5, message = "Username should have at least 5 characters")
    @Size(max = 15, message = "Username should not have more than 10 characters")
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

    @Size(min = 10)
    @Size(max = 140)
    @NotEmpty(message = "Description can not be empty")
    @JsonProperty("description")
    private String description;

    @JsonProperty("isEnabled")
    private Boolean isEnabled;

    @Size(min = 5, max = 14, message = "Password debe tener entre 5 y 14 caracteres")
    @NotEmpty(message = "Password can not be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty("idFreeArea")
    private Long idFreeArea;

    @JsonProperty("idPrivateArea")
    private Long idPrivateArea;

    @Min(value = 18, message = "Age from must be more than 18")
    @Max(value = 89, message = "Age to must be less than 90")
    @NotNull(message = "Age from must not be empty")
    @JsonProperty("ageFrom")
    private Long ageFrom;

    @Min(value = 19, message = "Age from must be more than 18")
    @Max(value = 90, message = "Age to must be less than 90")
    @NotNull(message = "Age to must not be empty")
    @JsonProperty("ageTo")
    private Long ageTo;

    @Pattern(regexp = "^[FM]$", message = "Sex preference must be 'F' or 'M'")
    @NotEmpty(message = "Sex preference must be 'F' or 'M'")
    @JsonProperty("sexPreference")
    private String sexPreference;

    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

    @Transient
    private Boolean admin = false;

    public Long getAge() {
        LocalDate today = LocalDate.now();
        long age = Period.between(birthdate, today).getYears();

        if (birthdate.getDayOfMonth() == today.getDayOfMonth() && birthdate.getMonth() == today.getMonth()) {
            return age + 1;
        }

        return age;
    }

}
