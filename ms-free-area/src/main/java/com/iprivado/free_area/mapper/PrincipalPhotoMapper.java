package com.iprivado.free_area.mapper;

import com.iprivado.free_area.dto.PrincipalPhotoDTO;
import com.iprivado.free_area.models.entity.PrincipalPhoto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrincipalPhotoMapper {

    PrincipalPhotoDTO toDTO(PrincipalPhoto principalPhoto);

    PrincipalPhoto toModel(PrincipalPhotoDTO principalPhotoDTO);
}
