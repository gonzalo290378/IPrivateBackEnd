package com.iprivado.ms_follow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponseDTO {
    private Long id;
    private UserSummaryDTO user;
    private LocalDate createdAt;
}
