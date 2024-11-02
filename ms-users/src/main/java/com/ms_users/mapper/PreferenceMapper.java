package com.ms_users.mapper;

import com.ms_users.dto.PreferenceDTO;
import com.ms_users.models.entity.Preference;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PreferenceMapper {
    public PreferenceDTO toDTO(Preference preference);

    public Preference toModel(PreferenceDTO preferenceDTO);
}
