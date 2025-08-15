package com.ms_users.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "city", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "id_state"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(message = "City should have not be empty")
    @Column(name = "city")
    private String city;

    @ManyToOne
    @JoinColumn(name = "id_state", nullable = false)
    @JsonBackReference("state-city")
    private State state;
}