package com.ms_users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("ageFrom")
    private Long ageFrom;

    @JsonProperty("ageTo")
    private Long ageTo;

    @JsonProperty("sexPreference")
    private String sexPreference;

}