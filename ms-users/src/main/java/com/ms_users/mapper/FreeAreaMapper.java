package com.ms_users.mapper;

import com.ms_users.models.FreeArea;
import com.ms_users.models.FreeAreaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PrincipalPhotoMapper.class, PublicContentMapper.class})
//@Mapper(componentModel = "spring")
public interface FreeAreaMapper {

    @Mapping(target = "principalPhotoDTO", source = "principalPhoto")
    @Mapping(target = "publicContentDTO", source = "publicContent")
    FreeAreaDTO toDTO(FreeArea freeArea);

    FreeArea toModel(FreeAreaDTO freeAreaDTO);
}
