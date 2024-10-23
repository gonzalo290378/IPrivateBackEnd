package com.ms_users.mapper;

import com.ms_users.dto.FilterDTO;
import com.ms_users.dto.UserDTO;
import com.ms_users.models.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilterMapper {
    public FilterDTO toDTO(User user);

    public User toModel(FilterDTO filterDTO);
}
