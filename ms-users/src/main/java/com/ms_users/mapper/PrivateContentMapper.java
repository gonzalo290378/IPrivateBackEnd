package com.ms_users.mapper;

import com.ms_users.models.PrivateContent;
import com.ms_users.models.PrivateContentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrivateContentMapper {
    PrivateContentDTO toDTO(PrivateContent privateContent);
    PrivateContent toModel(PrivateContentDTO privateContentDTO);
}
