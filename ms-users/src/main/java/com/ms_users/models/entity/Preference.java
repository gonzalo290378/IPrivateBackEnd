package com.ms_users.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @Column(name = "age_from")
    @Min(18)
    private Long ageFrom;

    @Column(name = "age_to")
    @Max(90)
    private Long ageTo;

    @Size(min = 10, message = "Sex Preference should have at least 5 characters")
    @NotEmpty(message = "Sex preference cannot be empty")
    @Column(name = "sex_preference")
    private String sexPreference;

}