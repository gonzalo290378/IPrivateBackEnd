package com.iprivado.free_area.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "public_content")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_free_area")
    //@JsonBackReference
    private FreeArea freeArea;

    @Temporal(TemporalType.DATE)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(unique = true, name = "date")
    private LocalDate date;

    @NotBlank
    @Size(max = 600, message = "The text must not exceed 600 characters")
    @Column(name = "description", unique = true)
    private String description;

    @Size(max = 255, message = "The contentUrl must not exceed 255 characters")
    @Column(name = "content_url")
    private String contentUrl;

    @Column(name = "like")
    private Long like;

}
