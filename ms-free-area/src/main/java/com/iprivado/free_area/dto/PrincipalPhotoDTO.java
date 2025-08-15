package com.iprivado.free_area.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class PrincipalPhotoDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("createdAt")
    private LocalDate createdAt;

    @JsonProperty("updatedAt")
    private LocalDate updatedAt;

}
