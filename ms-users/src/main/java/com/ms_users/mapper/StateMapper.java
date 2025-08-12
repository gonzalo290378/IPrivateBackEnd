package com.ms_users.mapper;

import com.ms_users.dto.CityDTO;
import com.ms_users.dto.StateDTO;
import com.ms_users.models.entity.City;
import com.ms_users.models.entity.State;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper {

    public StateDTO toDTO(State state);

    public State toModel(StateDTO stateDTO);
}

