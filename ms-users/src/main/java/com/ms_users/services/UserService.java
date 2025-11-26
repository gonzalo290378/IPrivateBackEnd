package com.ms_users.services;

import com.ms_users.dto.*;
import com.ms_users.models.entity.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {

    Page<UserDTO> findAll(Integer page, Integer size);

    Optional<UserProfileDTO> findById(Long id);

    Optional<UserProfileDTO> findByEmail(String email);

    Optional<User> findEntityByUsername(String username);

    Page<FilterDTO> filter(FilterDTO filterDTO, Integer page, Integer size);

    User save(UserFormDTO userFormDTO);

    User updateUserDetails(String username, UserDetailsFreeAreaDTO userDetailsFreeAreaDTO);

    void delete(Long id);

    Optional<User> findEntityById(Long id);

    String getAuthenticatedUsername();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserDTO> findByUsername(String username);

    void reactivateUser(Long id);
}
