package com.sprinboot.webflux.msauthserver.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Preference {

    private Long id;

    private Long ageFrom;

    private Long ageTo;

    private String sexPreference;

}