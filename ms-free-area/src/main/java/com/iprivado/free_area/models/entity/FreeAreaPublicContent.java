package com.iprivado.free_area.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "free-area-public-content")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeAreaPublicContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_content_id", unique = true)
    private Long publicContentId;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FreeAreaPublicContent)) {
            return false;
        }
        FreeAreaPublicContent o = (FreeAreaPublicContent) obj;
        return this.publicContentId != null && this.publicContentId.equals(o.publicContentId);
    }

}
