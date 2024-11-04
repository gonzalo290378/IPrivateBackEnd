package com.iprivado.private_area.mapper;

import com.iprivado.private_area.dto.PrivateContentDTO;
import com.iprivado.private_area.models.entity.PrivateContent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrivateContentMapper {

    PrivateContentDTO toDTO(PrivateContent privateContent);

    PrivateContent toModel(PrivateContentDTO privateContentDTO);
}
