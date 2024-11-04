package com.iprivado.private_area.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "private_area")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @OneToMany(mappedBy = "privateArea", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonManagedReference
    private List<PrivateContent> privateContent;

}
