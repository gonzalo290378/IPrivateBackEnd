package com.ms_users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateAreaUserDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("isEnabled")
    private Boolean isEnabled;
}
