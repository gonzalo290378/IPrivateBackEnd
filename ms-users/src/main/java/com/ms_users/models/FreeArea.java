package com.ms_users.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeArea {

    private Long id;

    private Boolean isEnabled;

    private List<PrincipalPhoto> principalPhoto;

    private List<PublicContent> publicContent;
}
