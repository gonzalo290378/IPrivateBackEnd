package com.iprivado.free_area.mapper;

import com.iprivado.free_area.dto.PrincipalPhotoDTO;
import com.iprivado.free_area.models.entity.PrincipalPhoto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrincipalPhotoMapper {

    PrincipalPhotoDTO toDTO(PrincipalPhoto principalPhoto);

    @Mapping(source = "idFreeArea", target = "freeArea.id")
    PrincipalPhoto toModel(PrincipalPhotoDTO principalPhotoDTO);
}
