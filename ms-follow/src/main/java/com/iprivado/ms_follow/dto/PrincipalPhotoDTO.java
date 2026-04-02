package com.iprivado.ms_follow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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

    @Column(name = "id_free_area")
    private Long idFreeArea;

    @JsonProperty("isEnabled")
    private Boolean isEnabled;

    @JsonProperty("url")
    private String url;

    @JsonProperty("createdAt")
    private LocalDate createdAt;

    @JsonProperty("updatedAt")
    private LocalDate updatedAt;

}
