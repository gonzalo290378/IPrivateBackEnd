package com.ms_users.mapper;

import com.ms_users.models.PrincipalPhoto;
import com.ms_users.models.PrincipalPhotoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrincipalPhotoMapper {

    PrincipalPhotoDTO toDTO(PrincipalPhoto principalPhoto);

    PrincipalPhoto toModel(PrincipalPhotoDTO principalPhotoDTO);
}
