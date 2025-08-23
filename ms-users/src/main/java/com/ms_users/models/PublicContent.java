package com.ms_users.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicContent {

    private Long id;

    private Long idFreeArea;

    private String description;

    private String contentUrl;

    private Long like;

    private LocalDate createdAt;

    private LocalDate updatedAt;


}
