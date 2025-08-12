package com.ms_users.mapper;

import com.ms_users.dto.FilterDTO;
import com.ms_users.models.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PreferenceMapper.class, CountryMapper.class, CityMapper.class, StateMapper.class})
public interface FilterMapper {

    @Mapping(target = "preferenceDTO", source = "preference")
    @Mapping(target = "cityDTO", source = "city")
    @Mapping(target = "countryDTO", source = "country")
    @Mapping(target = "stateDTO", source = "state")
    public FilterDTO toDTO(User user);

    public User toModel(FilterDTO filterDTO);
}
