package com.iprivado.private_area.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Size(min = 4, max = 400, message = "Description must be between 4 and 400 characters")
    @NotEmpty(message = "Description cannot be empty")
    @JsonProperty("description")
    private String description;

    @NotEmpty(message = "Private content URL can not be empty")
    @Column(name = "content_url")
    private String contentUrl;

    @Column(name = "like")
    private Long like;

}
