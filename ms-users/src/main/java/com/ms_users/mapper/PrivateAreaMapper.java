package com.ms_users.mapper;

import com.ms_users.models.PrivateArea;
import com.ms_users.models.PrivateAreaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PrivateContentMapper.class})
public interface PrivateAreaMapper {

    @Mapping(target = "privateContentDTO", source = "privateContent")
    PrivateAreaDTO toDTO(PrivateArea privateArea);

    PrivateArea toModel(PrivateAreaDTO privateAreaDTO);
}
