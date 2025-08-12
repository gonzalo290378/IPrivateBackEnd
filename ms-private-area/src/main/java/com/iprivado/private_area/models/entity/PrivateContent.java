package com.iprivado.private_area.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "private_content")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_private_area")
    private PrivateArea privateArea;

    @Temporal(TemporalType.DATE)
    @Column(unique = true, name = "date")
    private LocalDate date;

    @Size(min = 10, message = "The description must have more than 10 characters")
    @Size(max = 600, message = "The description must not exceed 600 characters")
    @NotEmpty(message = "Description can not be empty")
    @Column(name = "description", unique = true)
    private String description;

    @NotEmpty(message = "Private content URL can not be empty")
    @Column(name = "content_url")
    private String contentUrl;

    @Column(name = "like")
    private Long like;

}
