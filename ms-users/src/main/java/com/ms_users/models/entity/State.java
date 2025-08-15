package com.ms_users.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "state", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "id_country"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(message = "State should have not be empty")
    @Column(name = "state")
    private String state;

    @ManyToOne
    @JoinColumn(name = "id_country", nullable = false)
    @JsonBackReference("country-state")
    private Country country;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    @JsonManagedReference("state-city")
    private List<City> city;
}