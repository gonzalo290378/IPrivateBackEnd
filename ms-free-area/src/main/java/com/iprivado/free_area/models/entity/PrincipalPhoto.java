package com.iprivado.free_area.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    private FreeArea freeArea;

    @NotEmpty(message = "Url content can not be empty")
    @Size(max = 255, message = "The url principal photo must have 255 characters as max.")
    @Column(name = "url")
    private String url;


}
