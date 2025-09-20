package com.iprivado.free_area.mapper;

import com.iprivado.free_area.dto.PublicContentDTO;
import com.iprivado.free_area.models.entity.PublicContent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublicContentMapper {

    PublicContentDTO toDTO(PublicContent publicContent);

    @Mapping(source = "idFreeArea", target = "freeArea.id")
    PublicContent toModel(PublicContentDTO publicContentDTO);
}
