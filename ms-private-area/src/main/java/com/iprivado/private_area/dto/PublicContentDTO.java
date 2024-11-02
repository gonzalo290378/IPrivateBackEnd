package com.iprivado.free_area.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDate;

public class PublicContentDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("idFreeArea")
    private Long idFreeArea;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("description")
    private String description;

    @JsonProperty("contentUrl")
    private String contentUrl;

    @JsonProperty("like")
    private Long like;
}
