package com.iprivado.messages.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeAreaDTO {

    private Long id;

    private List<PrincipalPhotoDTO> principalPhotoDTO;
}
