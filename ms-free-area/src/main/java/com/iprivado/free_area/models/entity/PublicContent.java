package com.iprivado.free_area.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    private FreeArea freeArea;

    @Temporal(TemporalType.DATE)
    @Column(unique = true, name = "date")
    private LocalDate date;

    @Size(min = 10, message = "The description must have more than 10 characters")
    @Size(max = 600, message = "The description must not exceed 144 characters")
    @NotEmpty(message = "Description can not be empty")
    @Column(name = "description", unique = true)
    private String description;

    @Size(min = 10)
    @Size(max = 255, message = "The contentUrl must not exceed 255 characters")
    @Column(name = "content_url")
    private String contentUrl;

    @Column(name = "like")
    private Long like;

}
