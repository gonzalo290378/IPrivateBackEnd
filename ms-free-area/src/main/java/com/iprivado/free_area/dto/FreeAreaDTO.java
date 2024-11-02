package com.iprivado.free_area.dto;

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
public class FreeAreaDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("isEnabled")
    private Boolean isEnabled;

    @JsonProperty("principalPhotoDTO")
    private List<PrincipalPhotoDTO> principalPhotoDTO;

    @JsonProperty("publicContentDTO")
    private List<PublicContentDTO> publicContentDTO;

}
