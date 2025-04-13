package com.sprinboot.webflux.ms_oauth.models;

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