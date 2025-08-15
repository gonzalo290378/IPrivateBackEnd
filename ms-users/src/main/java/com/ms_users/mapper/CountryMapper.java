package com.ms_users.mapper;

import com.ms_users.dto.CountryDTO;
import com.ms_users.models.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StateMapper.class})
public interface CountryMapper {

    @Mapping(source = "states", target = "state")
    CountryDTO toDTO(Country country);

    @Mapping(source = "state", target = "states")
    Country toModel(CountryDTO countryDTO);

    List<CountryDTO> toDTOList(List<Country> countries);

    List<Country> toModelList(List<CountryDTO> countriesDTO);
}
