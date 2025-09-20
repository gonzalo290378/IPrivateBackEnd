package com.iprivado.free_area.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "free_area")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"principalPhoto", "publicContent"})
public class FreeArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @OneToMany(mappedBy = "freeArea", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PrincipalPhoto> principalPhoto;

    @OneToMany(mappedBy = "freeArea", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PublicContent> publicContent;
}
