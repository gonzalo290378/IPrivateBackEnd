package com.ms_users.services;

import com.ms_users.dto.FilterDTO;
import com.ms_users.dto.UserDTO;
import com.ms_users.dto.UserFormDTO;
import com.ms_users.models.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<UserDTO> findAll(Integer page, Integer size);

    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> findByEmail(String email);

    Page<FilterDTO> filter(FilterDTO filterDTO, Integer page, Integer size);

    User save(UserFormDTO userFormDTO);

    User update(UserFormDTO userFormDTO, User user);

    User delete(Long id);

    Optional<User> findEntityById(Long id);

}
