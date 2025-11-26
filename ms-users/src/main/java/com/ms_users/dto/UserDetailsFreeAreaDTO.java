package com.ms_users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsFreeAreaDTO implements Serializable {

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("birthdate")
    private LocalDate birthdate;

    @Pattern(regexp = "^(F|M|T|NB)$", message = "Sex preference must be 'F', 'M', 'T' or 'NB'")
    @NotEmpty(message = "Sex preference must be 'F', 'M', 'T' or 'NB' ")
    @JsonProperty("sex")
    private String sex;

    @Size(min = 4, max = 400, message = "Description must be between 60 and 140 characters")
    @NotEmpty(message = "Description cannot be empty")
    @JsonProperty("description")
    private String description;

    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

}


