package com.ms_users.mapper;

import com.ms_users.dto.FreeAreaUserDTO;
import com.ms_users.dto.UserDTO;
import com.ms_users.models.entity.FreeAreaUser;
import com.ms_users.models.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FreeAreaMapper {
    public FreeAreaUserDTO toDTO(FreeAreaUser freeAreaUser);

    public User toModel(UserDTO userResponseDTO);
}
