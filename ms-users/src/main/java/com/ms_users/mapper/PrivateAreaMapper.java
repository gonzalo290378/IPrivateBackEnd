package com.ms_users.mapper;

import com.ms_users.dto.PrivateAreaUserDTO;
import com.ms_users.dto.UserDTO;
import com.ms_users.models.PrivateAreaUser;
import com.ms_users.models.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrivateAreaMapper {
    public PrivateAreaUserDTO toDTO(PrivateAreaUser privateAreaUser);

    public User toModel(UserDTO userResponseDTO);
}
