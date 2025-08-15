package com.ms_users.mapper;

import com.ms_users.dto.StateDTO;
import com.ms_users.models.entity.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface StateMapper {

    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "city", target = "city")
    StateDTO toDTO(State state);

    @Mapping(source = "countryId", target = "country.id")
    State toModel(StateDTO stateDTO);
}

