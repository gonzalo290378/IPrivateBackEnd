package com.ms_users.services;

import com.ms_users.clients.FreeAreaClientRest;
import com.ms_users.clients.PrivateAreaClientRest;
import com.ms_users.dto.FilterDTO;
import com.ms_users.dto.UserDTO;
import com.ms_users.dto.UserFormDTO;
import com.ms_users.enums.AgeConfiguration;
import com.ms_users.enums.AreaConfiguration;
import com.ms_users.enums.DateConfiguration;
import com.ms_users.enums.UserEnabledConfiguration;
import com.ms_users.exceptions.*;
import com.ms_users.mapper.FilterMapper;
import com.ms_users.mapper.UserMapper;
import com.ms_users.models.FreeAreaDTO;
import com.ms_users.models.PrivateAreaDTO;
import com.ms_users.models.entity.City;
import com.ms_users.models.entity.Country;
import com.ms_users.models.entity.Preference;
import com.ms_users.models.entity.User;
import com.ms_users.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        List<User> userList = userRepository.findByIsEnabledTrueOrderByIdDesc();
        List<FreeAreaDTO> freeAreaList = freeAreaClientRest.findAll();
        List<PrivateAreaDTO> privateAreaList = privateAreaClientRest.findAll();
        List<UserDTO> userDTOList = userList.stream().map(userMapper::toDTO).toList();
        return matchUserWithFreeAreaAndPrivateArea(userDTOList, freeAreaList, privateAreaList);
    }

    private List<UserDTO> matchUserWithFreeAreaAndPrivateArea(List<UserDTO> userDTOList, List<FreeAreaDTO> freeAreaDTOList, List<PrivateAreaDTO> privateAreaDTOList
    ) {
        for (UserDTO user : userDTOList) {
            freeAreaDTOList.stream()
                    .filter(freeArea -> Objects.equals(freeArea.getId(), user.getIdFreeArea()))
                    .peek(user::setFreeAreaDTO)
                    .collect(Collectors.toList());

            privateAreaDTOList.stream()
                    .filter(privateArea -> Objects.equals(privateArea.getId(), user.getIdPrivateArea()) && user.getIsEnabled())
                    .peek(user::setPrivateAreaDTO)
                    .collect(Collectors.toList());
        }
        return userDTOList;
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        User user = userRepository.findAll().stream().filter(e -> Objects.equals(e.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IdNotFoundException("id: " + id + " does not exist"));
        return getUserDTO(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findEntityById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new IdNotFoundException("id: " + id + " does not exist")));
    }
    public Optional<UserDTO> findByEmail(String email) {
        User user = userRepository.findAll().stream().filter(e -> Objects.equals(e.getEmail(), email))
                .findFirst()
                .orElseThrow(() -> new EmailNotFoundException("email: " + email + " does not exist"));
        return getUserDTO(user);
    }

    public Optional<UserDTO> findByUsername(String username){
        User user = userRepository.findByIsEnabledTrueOrderByIdDesc().stream().filter(e -> Objects.equals(e.getUsername(), username))
                .findFirst()
                .orElseThrow(() -> new EmailNotFoundException("username: " + username + " does not exist"));
        return getUserDTO(user);
    }

    private Optional<UserDTO> getUserDTO(User user) {
        FreeAreaDTO freeAreaDTO = freeAreaClientRest.findById(user.getIdFreeArea());
        PrivateAreaDTO privateAreaDTO = privateAreaClientRest.findById(user.getIdPrivateArea());
        UserDTO userDTO = userMapper.toDTO(user);
        userDTO.setFreeAreaDTO(freeAreaDTO);
        userDTO.setPrivateAreaDTO(privateAreaDTO);
        return Optional.of(userDTO);
    }

    @Transactional(readOnly = true)
    public Page<FilterDTO> filter(FilterDTO filterDTO, Integer page, Integer size) {
        validateUserAgeSelected(filterDTO);
        return getFilteredUsers(filterDTO, page, size);
    }

    private Page<FilterDTO> getFilteredUsers(FilterDTO filterDTO, Integer page, Integer size) {
        Page<User> filterUserList = userRepository.filter(filterDTO, PageRequest.of(page, size));

        return filterUserList.map(user -> {
            FilterDTO filterListDTO = filterMapper.toDTO(user);
            FreeAreaDTO freeAreaDTO = freeAreaClientRest.findById(user.getIdFreeArea());
            PrivateAreaDTO privateAreaDTO = privateAreaClientRest.findById(user.getIdPrivateArea());
            filterListDTO.setFreeAreaDTO(freeAreaDTO);
            filterListDTO.setPrivateAreaDTO(privateAreaDTO);
            filterListDTO.setIdFreeArea(freeAreaDTO.getId());
            return filterListDTO;
        });
    }

    private void validateUserAgeSelected(FilterDTO filterDTO) {
        Long ageFrom = filterDTO.getPreferenceDTO().getAgeFrom();
        Long ageTo = filterDTO.getPreferenceDTO().getAgeTo();

        Boolean isAgeFromTooLow = ageFrom < AgeConfiguration.ADULT.getValue();
        Boolean isAgeRangeInvalid = ageFrom > ageTo;
        Boolean isAgeToTooHigh = ageTo > AgeConfiguration.SENIOR.getValue();

        if (isAgeFromTooLow || isAgeRangeInvalid || isAgeToTooHigh) {
            throw new UserAgeSelectedException("User: " + filterDTO.getUsername()
                    + " has selected an invalid age range: ageFrom = " + ageFrom + ", ageTo = " + ageTo);
        }
    }

    public User save(UserFormDTO userFormDTO) {
        Boolean userEmailExists = findByEmailWithoutException(userFormDTO.getEmail());
        Boolean usernameExists = findByUsernameWithoutException(userFormDTO.getUsername());
        if (!userEmailExists && !usernameExists) {
            validateUserForm(userFormDTO);
            User newUser = buildUser(userFormDTO);
            return userRepository.save(newUser);
        }
        throw new UsernameNotFoundException("User: " + userFormDTO.getUsername()
                + " is registered");

    }

    public Boolean findByEmailWithoutException(String email) {
        Optional<User> user = userRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getEmail(), email))
                .findFirst();
        return user.isPresent();
    }


    public Boolean findByUsernameWithoutException(String username) {
        Optional<User> user = userRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getUsername(), username))
                .findFirst();
        return user.isPresent();
    }


    private void validateUserForm(UserFormDTO userFormDTO) {
        validateAgeFromAndAgeTo(userFormDTO.getAgeFrom(), userFormDTO.getAgeTo());
        validateBirthdate(userFormDTO.getBirthdate());
    }

    private void validateAgeFromAndAgeTo(Long ageFrom, Long ageTo) {
        if (ageFrom != null && ageTo != null && ageFrom > ageTo) {
            throw new InvalidAgeRangeException("Age from cannot be greater than age to");
        }
    }

    private void validateBirthdate(LocalDate birthdate) {
        if (!isAdult(birthdate)) {
            throw new InvalidBirthdateException("Age must be at least 18 years old.");
        }
    }

    private User buildUser(UserFormDTO userFormDTO) {
        FreeAreaDTO newFreeAreaDTO = createFreeArea();
        PrivateAreaDTO newPrivateArea = createPrivateArea();
        Preference preference = buildPreference(userFormDTO);
        Country country = buildCountry(userFormDTO);
        City city = buildCity(userFormDTO);
        return buildUser(userFormDTO, newFreeAreaDTO, newPrivateArea, preference, country, city);
    }


    private boolean isAdult(LocalDate birthdate) {
        return birthdate != null && ChronoUnit.YEARS.between(birthdate, LocalDate.now()) >= 18;
    }

    private FreeAreaDTO createFreeArea() {
        return freeAreaClientRest.save(AreaConfiguration.ENABLED.getValue());
    }

    private PrivateAreaDTO createPrivateArea() {
        return privateAreaClientRest.save(AreaConfiguration.DISABLED.getValue());
    }

    private Preference buildPreference(UserFormDTO userFormDTO) {
        return Preference.builder()
                .ageFrom(userFormDTO.getAgeFrom())
                .ageTo(userFormDTO.getAgeTo())
                .sexPreference(userFormDTO.getSexPreference())
                .build();
    }

    private Country buildCountry(UserFormDTO userFormDTO) {
        return Country.builder()
                .country(userFormDTO.getCountry())
                .build();
    }

    private City buildCity(UserFormDTO userFormDTO) {
        return City.builder()
                .city(userFormDTO.getCity())
                .build();
    }

    private User buildUser(UserFormDTO userFormDTO, FreeAreaDTO freeAreaDTO, PrivateAreaDTO privateAreaDTO,
                           Preference preference, Country country, City city) {
        return User.builder()
                .idFreeArea(freeAreaDTO.getId())
                .idPrivateArea(privateAreaDTO.getId())
                .preference(preference)
                .country(country)
                .city(city)
                .username(userFormDTO.getUsername())
                .age(userFormDTO.getAge())
                .sex(userFormDTO.getSex())
                .email(userFormDTO.getEmail())
                .birthdate(userFormDTO.getBirthdate())
                .registerDate(DateConfiguration.TODAY.getValue())
                .description(userFormDTO.getDescription())
                .isEnabled(UserEnabledConfiguration.IS_ENABLED.getValue())
                .password(userFormDTO.getPassword())
                .build();
    }

    public User update(UserFormDTO userFormDTO, User user) {
        validateUserForm(userFormDTO);
        updateUserPreferences(user, userFormDTO);
        updateUserLocation(user, userFormDTO);
        updateUserBasicInfo(user, userFormDTO);
        return userRepository.save(user);
    }

    private void updateUserPreferences(User user, UserFormDTO userFormDTO) {
        user.getPreference().setAgeFrom(userFormDTO.getAgeFrom());
        user.getPreference().setAgeTo(userFormDTO.getAgeTo());
        user.getPreference().setSexPreference(userFormDTO.getSexPreference());
    }

    private void updateUserLocation(User user, UserFormDTO userFormDTO) {
        user.getCountry().setCountry(userFormDTO.getCountry());
        user.getCity().setCity(userFormDTO.getCity());
    }

    private void updateUserBasicInfo(User user, UserFormDTO userFormDTO) {
        user.setUsername(userFormDTO.getUsername());
        user.setAge(userFormDTO.getAge());
        user.setBirthdate(userFormDTO.getBirthdate());
        user.setDescription(userFormDTO.getDescription());
        user.setPassword(userFormDTO.getPassword());
    }


    @Transactional
    public User delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User: is not registered");
        }
        userRepository.delete(id);
        freeAreaClientRest.delete(user.get().getIdFreeArea());
        privateAreaClientRest.delete(user.get().getIdPrivateArea());
        return user.get();
    }

}
