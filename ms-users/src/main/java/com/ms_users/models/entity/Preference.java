package com.ms_users.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preference")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 18, message = "Age from must be more than 18")
    @Max(value = 90, message = "Age to must be less than 90")
    @NotNull(message = "Age from must not be empty")
    @Column(name = "age_from")
    private Long ageFrom;

    @Min(value = 18, message = "Age from must be more than 18")
    @Max(value = 90, message = "Age to must be less than 90")
    @NotNull(message = "Age to must not be empty")
    @Column(name = "age_to")
    private Long ageTo;

    @Pattern(regexp = "^(F|M|T|NB)$", message = "Sex preference must be 'F', 'M', 'T' or 'NB'")
    @NotEmpty(message = "Sex preference must be 'F', 'M', 'T' or 'NB' ")
    @JsonProperty("sexPreference")
    private String sexPreference;

}