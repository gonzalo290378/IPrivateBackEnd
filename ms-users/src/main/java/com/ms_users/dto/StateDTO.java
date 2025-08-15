package com.ms_users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("state")
    private String state;

    @JsonProperty("id_country")
    private Long countryId;

    @JsonProperty("city")
    private List<CityDTO> city;
}