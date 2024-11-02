package com.iprivado.free_area.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class PrincipalPhotoDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("idFreeArea")
    private Long idFreeArea;

    @JsonProperty("url")
    private String url;

}
