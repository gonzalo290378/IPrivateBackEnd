package com.ms_users.services;

import com.ms_users.dto.FilterDTO;
import com.ms_users.dto.UserDTO;
import com.ms_users.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> findByEmail(String email);

    Page<FilterDTO> filter(FilterDTO filterDTO, Integer page, Integer size);

    User save(User user);

    void delete(Long id);

    Boolean hasInvalidFields(User user, UserDTO userDTO);

    ResponseEntity<Map<String, String>> validate(BindingResult result);

}
