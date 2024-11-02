package com.iprivado.free_area.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "principal_photo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_free_area")
    //@JsonBackReference
    private FreeArea freeArea;

    @Column(name = "url")
    @Size(max = 255, message = "The url must have 255 characters as max.")
    private String url;


}
