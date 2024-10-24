package com.ms_users.services;

import com.ms_users.clients.FreeAreaClientRest;
import com.ms_users.dto.UserDTO;
import com.ms_users.exceptions.EmailNotFoundException;
import com.ms_users.exceptions.IdNotFoundException;
import com.ms_users.exceptions.UserDisabledNotFoundException;
import com.ms_users.mapper.UserMapper;
import com.ms_users.models.entity.FreeArea;
import com.ms_users.models.entity.User;
import com.ms_users.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final FreeAreaClientRest freeAreaClientRest;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, FreeAreaClientRest client) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.freeAreaClientRest = client;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        User user = userRepository.findAll().stream().filter(e -> Objects.equals(e.getId(), id)).findFirst().orElseThrow(() -> new IdNotFoundException("id: " + id + " does not exist"));
        return Optional.of(userMapper.toDTO(user));
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findByEmail(String email) {
        User user = userRepository.findAll().stream().filter(e -> Objects.equals(e.getEmail(), email)).findFirst().orElseThrow(() -> new EmailNotFoundException("email: " + email + " does not exist"));
        return Optional.of(userMapper.toDTO(user));
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> filter(UserDTO userListDTO, Integer page, Integer size) {
        validateUserIsEnabled(userListDTO);
        return getFilteredUsers(userListDTO, page, size);
    }

    //TODO VER QUE TIPO DE REQUIRIMIENTOS DEBE CUMPLIR EL USUARIO PARA VOLVER A HABILITARLO
    @Transactional
    public User save(User user) {

        validateUserIsEnabled(userMapper.toDTO(user));
        FreeArea newFreeArea = freeAreaClientRest.save(new FreeArea());

        User newUser = new User().builder().freeArea(newFreeArea).username(user.getUsername()).email(user.getEmail()).birthdate(user.getBirthdate()).city(user.getCity()).country(user.getCountry()).registerDate(user.getRegisterDate()).description(user.getDescription()).isEnabled(true).build();
        return userRepository.save(newUser);
    }

    @Transactional
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(id);
            freeAreaClientRest.delete(id);
        }
    }

    public ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "The field " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    //TODO FALTA INSERTAR LAS FOTOS (O CONTENIDO)
    public Boolean hasInvalidFields(User user, UserDTO usuarioDb) {
        return !user.getFreeArea().getId().equals(usuarioDb.getFreeAreaUser().getId()) ||
                //!user.getIdContent().equals(usuarioDb.getIdContent()) ||
                isEmpty(user.getUsername()) || isEmpty(user.getEmail()) ||
                //isEmpty(user.getBirthdate()) ||
                isEmpty(user.getCity()) || isEmpty(user.getCountry()) || isEmpty(user.getPassword());
    }

    private Boolean isEmpty(String field) {
        return field == null || field.isEmpty();
    }

    private Page<UserDTO> getFilteredUsers(UserDTO userListDTO, Integer page, Integer size) {
        Page<User> filterUserList = userRepository.filter(userListDTO, PageRequest.of(page, size));
        return filterUserList.map(userMapper::toDTO);
    }

    private void validateUserIsEnabled(UserDTO userDTO) {
        if (!userDTO.getIsEnabled()) {
            throw new UserDisabledNotFoundException("user: " + userDTO.getUsername() + " is disabled");
        }
    }

}
