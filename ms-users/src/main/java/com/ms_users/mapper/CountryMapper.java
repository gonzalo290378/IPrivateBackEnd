package com.ms_users.mapper;

import com.ms_users.dto.CountryDTO;
import com.ms_users.dto.PreferenceDTO;
import com.ms_users.models.entity.Country;
import com.ms_users.models.entity.Preference;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    public CountryDTO toDTO(Country country);

    public Country toModel(CountryDTO countryDTO);
}
