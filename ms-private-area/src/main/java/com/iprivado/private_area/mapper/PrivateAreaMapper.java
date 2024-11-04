package com.iprivado.private_area.mapper;

import com.iprivado.private_area.dto.PrivateAreaDTO;
import com.iprivado.private_area.models.entity.PrivateArea;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PrivateContentMapper.class})
public interface PrivateAreaMapper {

    @Mapping(target = "privateContentDTO", source = "privateContent")
    PrivateAreaDTO toDTO(PrivateArea privateArea);

    PrivateArea toModel(PrivateAreaDTO freeAreaDTO);
}
