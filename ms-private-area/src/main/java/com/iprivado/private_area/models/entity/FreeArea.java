package com.iprivado.free_area.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "free_area")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @OneToMany(mappedBy = "freeArea", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonManagedReference
    private List<PrincipalPhoto> principalPhoto;

    @OneToMany(mappedBy = "freeArea", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonManagedReference
    private List<PublicContent> publicContent;
}
