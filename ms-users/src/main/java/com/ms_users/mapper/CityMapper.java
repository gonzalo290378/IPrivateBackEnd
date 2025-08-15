package com.ms_users.mapper;

import com.ms_users.dto.CityDTO;
import com.ms_users.models.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(source = "state.id", target = "stateId")
    CityDTO toDTO(City city);

    @Mapping(source = "stateId", target = "state.id")
    City toModel(CityDTO cityDTO);
}
