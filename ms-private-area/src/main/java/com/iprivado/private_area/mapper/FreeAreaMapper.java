package com.iprivado.free_area.mapper;

import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.models.entity.FreeArea;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FreeAreaMapper {

    FreeAreaDTO toDTO(FreeArea freeArea);

    FreeArea toModel(FreeAreaDTO freeAreaDTO);
}
