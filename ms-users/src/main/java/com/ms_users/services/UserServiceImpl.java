package com.ms_users.services;

import com.ms_users.clients.FreeAreaClientRest;
import com.ms_users.clients.PrivateAreaClientRest;
import com.ms_users.dto.FilterDTO;
import com.ms_users.dto.UserDTO;
import com.ms_users.enums.AgeConfiguration;
import com.ms_users.exceptions.EmailNotFoundException;
import com.ms_users.exceptions.IdNotFoundException;
import com.ms_users.exceptions.UserDisabledNotFoundException;
import com.ms_users.mapper.FilterMapper;
import com.ms_users.mapper.UserMapper;
import com.ms_users.models.FreeArea;
import com.ms_users.models.PrivateArea;
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

    private final FilterMapper filterMapper;

    private final FreeAreaClientRest freeAreaClientRest;

    private final PrivateAreaClientRest privateAreaClientRest;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, FilterMapper filterMapper, FreeAreaClientRest client, PrivateAreaClientRest privateAreaClientRest) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.filterMapper = filterMapper;
        this.freeAreaClientRest = client;
        this.privateAreaClientRest = privateAreaClientRest;
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
    public Page<FilterDTO> filter(FilterDTO filterDTO, Integer page, Integer size) {
        validateUserAgeSelected(filterDTO);
        return getFilteredUsers(filterDTO, page, size);
    }

    private void validateUserAgeSelected(FilterDTO filterDTO) {
        Long ageFrom = filterDTO.getPreferenceDTO().getAgeFrom();
        Long ageTo = filterDTO.getPreferenceDTO().getAgeTo();

        Boolean isAgeFromTooLow = ageFrom < AgeConfiguration.ADULT.getValue();
        Boolean isAgeRangeInvalid = ageFrom > ageTo;
        Boolean isAgeToTooHigh = ageTo > AgeConfiguration.SENIOR.getValue();

        if (isAgeFromTooLow || isAgeRangeInvalid || isAgeToTooHigh) {
            throw new UserDisabledNotFoundException("User: " + filterDTO.getUsername()
                    + " has selected an invalid age range: ageFrom = " + ageFrom + ", ageTo = " + ageTo);
        }
    }

    private Page<FilterDTO> getFilteredUsers(FilterDTO filterDTO, Integer page, Integer size) {
        Page<User> filterUserList = userRepository.filter(filterDTO, PageRequest.of(page, size));
        return filterUserList.map(filterMapper::toDTO);
    }

    @Transactional
    public User save(User user) {

        FreeArea newFreeArea = freeAreaClientRest.save(new FreeArea());
        PrivateArea newPrivateArea = privateAreaClientRest.save(new PrivateArea());

        User newUser = new User().builder()
                .freeArea(newFreeArea)
                .privateArea(newPrivateArea)
                .age(user.getAge())
                // .ageFrom(user.getAgeFrom())
                //  .ageTo(user.getAgeTo())
                .username(user.getUsername())
                .sex(user.getSex())
                //.sexPreference(user.getSexPreference())
                .email(user.getEmail())
                .birthdate(user.getBirthdate())
                .city(user.getCity())
                .country(user.getCountry())
                //.registerDate(DateConfiguration.TODAY.getValue())
                // .isEnabled(UserEnabledConfiguration.IS_ENABLED.getValue())
                .password(user.getPassword()).build();
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
                isEmpty(user.getCity().getCity()) || isEmpty(user.getCountry().getCountry()) || isEmpty(user.getPassword());
    }

    private Boolean isEmpty(String field) {
        return field == null || field.isEmpty();
    }

}
