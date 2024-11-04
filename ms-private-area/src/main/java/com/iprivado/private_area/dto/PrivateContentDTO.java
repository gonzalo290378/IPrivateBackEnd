package com.iprivado.private_area.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;

public class PrivateContentDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("idPrivateArea")
    private Long idPrivateArea;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("description")
    private String description;

    @JsonProperty("contentUrl")
    private String contentUrl;

    @JsonProperty("like")
    private Long like;
}
