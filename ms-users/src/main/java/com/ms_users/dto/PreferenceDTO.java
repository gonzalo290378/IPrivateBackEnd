package com.ms_users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @Min(value = 18, message = "Age from must be more than 18")
    @Max(value = 90, message = "Age to must be less than 90")
    @NotNull(message = "Age from must not be empty")
    @JsonProperty("ageFrom")
    private Long ageFrom;

    @Min(value = 18, message = "Age from must be more than 18")
    @Max(value = 90, message = "Age to must be less than 90")
    @NotNull(message = "Age to must not be empty")
    @JsonProperty("ageTo")
    private Long ageTo;

    @Pattern(regexp = "^[FM]$", message = "Sex preference must be 'F' or 'M'")
    @NotEmpty(message = "Sex preference must be 'F' or 'M'")
    @JsonProperty("sexPreference")
    private String sexPreference;

}