package com.ms_users.mapper;

import com.ms_users.dto.UserDTO;
import com.ms_users.models.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PreferenceMapper.class, CountryMapper.class, CityMapper.class, StateMapper.class, FreeAreaMapper.class, PrivateAreaMapper.class})
public interface UserMapper {

    @Mapping(target = "preferenceDTO", source = "preference")
    @Mapping(target = "cityDTO", source = "city")
    @Mapping(target = "countryDTO", source = "country")
    @Mapping(target = "stateDTO", source = "state")
    public UserDTO toDTO(User user);

    public User toModel(UserDTO userDTO);

}

