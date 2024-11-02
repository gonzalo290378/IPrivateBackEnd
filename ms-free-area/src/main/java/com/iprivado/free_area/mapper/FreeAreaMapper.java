package com.iprivado.free_area.mapper;

import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.models.entity.FreeArea;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PrincipalPhotoMapper.class, PublicContentMapper.class})
public interface FreeAreaMapper {

    @Mapping(target = "principalPhotoDTO", source = "principalPhoto")
    @Mapping(target = "publicContentDTO", source = "publicContent")
    FreeAreaDTO toDTO(FreeArea freeArea);

    FreeArea toModel(FreeAreaDTO freeAreaDTO);
}
