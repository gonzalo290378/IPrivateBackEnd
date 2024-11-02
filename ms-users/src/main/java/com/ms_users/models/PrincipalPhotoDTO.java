package com.ms_users.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PrincipalPhotoDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("url")
    private String url;
}
