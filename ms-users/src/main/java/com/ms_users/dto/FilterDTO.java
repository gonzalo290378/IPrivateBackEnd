package com.ms_users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms_users.models.FreeAreaDTO;
import com.ms_users.models.PrivateArea;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("idFreeArea")
    private Long idFreeArea;

    @JsonProperty("idPrivateArea")
    private Long idPrivateArea;

    @JsonProperty("freeAreaDTO")
    private FreeAreaDTO freeAreaDTO;

    @JsonProperty("privateArea")
    private PrivateArea privateArea;

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

}
