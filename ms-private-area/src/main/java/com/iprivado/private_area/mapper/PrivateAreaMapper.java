package com.iprivado.private_area.mapper;

import com.iprivado.private_area.dto.PrivateAreaDTO;
import com.iprivado.private_area.models.entity.PrivateArea;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PrivateContentMapper.class})
public interface PrivateAreaMapper {

    PrivateAreaDTO toDTO(PrivateArea freeArea);

    PrivateArea toModel(PrivateAreaDTO freeAreaDTO);
}
