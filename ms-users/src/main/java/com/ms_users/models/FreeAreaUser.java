package com.ms_users.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "free_area_users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeAreaUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user", unique = true)
    private Long idUser;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

}
