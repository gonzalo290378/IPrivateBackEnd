package com.ms_users.mapper;

import com.ms_users.dto.CityDTO;
import com.ms_users.models.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    public CityDTO toDTO(City city);

    public City toModel(CityDTO cityDTO);
}
