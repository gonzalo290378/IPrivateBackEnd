package com.iprivado.messages.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalPhotoDTO {

    private Long id;

    private String url;

    private Long idFreeArea;


}
