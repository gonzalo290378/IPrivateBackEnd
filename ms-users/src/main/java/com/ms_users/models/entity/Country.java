package com.ms_users.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "country", uniqueConstraints = @UniqueConstraint(columnNames = "country"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(message = "Country should have not be empty")
    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    @JsonManagedReference("country-state")
    private List<State> states;

}