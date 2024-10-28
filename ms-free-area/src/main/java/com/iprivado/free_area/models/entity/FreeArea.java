package com.iprivado.free_area.models.entity;

import com.iprivado.free_area.models.PublicContent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "free-area-users")
@Data
@Builder
@AllArgsConstructor
public class FreeArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "free_area_content_id")
    private List<FreeAreaPublicContent> freeAreaContentList;

    @Transient
    private List<PublicContent> publicContentList;

    public FreeArea() {
        this.freeAreaContentList = new ArrayList<>();
        this.publicContentList = new ArrayList<>();
    }

    public void addFreeAreaContent(FreeAreaPublicContent freeAreaContent) {
        freeAreaContentList.add(freeAreaContent);
    }

    public void removeFreeAreaContent(FreeAreaPublicContent freeAreaContent) {
        freeAreaContentList.remove(freeAreaContent);
    }


}
