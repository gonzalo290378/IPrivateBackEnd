package com.ms_users.models;

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
public class PrincipalPhotoDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("url")
    private String url;
}
