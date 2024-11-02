package com.ms_users.mapper;

import com.ms_users.models.PublicContent;
import com.ms_users.models.PublicContentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicContentMapper {

    PublicContentDTO toDTO(PublicContent publicContent);

    PublicContent toModel(PublicContentDTO publicContentDTO);
}
