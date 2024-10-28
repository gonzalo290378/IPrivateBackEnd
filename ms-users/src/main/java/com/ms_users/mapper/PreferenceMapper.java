package com.ms_users.mapper;

import com.ms_users.dto.PreferenceDTO;
import com.ms_users.dto.PrivateAreaUserDTO;
import com.ms_users.dto.UserDTO;
import com.ms_users.models.entity.Preference;
import com.ms_users.models.entity.PrivateAreaUser;
import com.ms_users.models.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PreferenceMapper {
    public PreferenceDTO toDTO(Preference preference);

    public Preference toModel(PreferenceDTO preferenceDTO);
}
