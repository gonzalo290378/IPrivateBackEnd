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
public class CountryDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("country")
    private String country;

    @JsonProperty("state")
    private List<StateDTO> state;
}