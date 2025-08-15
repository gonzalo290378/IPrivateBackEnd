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
import com.ms_users.models.entity.*;
import com.ms_users.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final FilterMapper filterMapper;
    private final FreeAreaClientRest freeAreaClientRest;
    private final PrivateAreaClientRest privateAreaClientRest;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserServiceImpl(UserRepository userRepository, CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository, RoleRepository roleRepository, UserMapper userMapper, FilterMapper filterMapper, FreeAreaClientRest client, PrivateAreaClientRest privateAreaClientRest) {
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.filterMapper = filterMapper;
        this.freeAreaClientRest = client;
        this.privateAreaClientRest = privateAreaClientRest;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Integer page, Integer size) {
        Page<User> userPage = userRepository.findByIsEnabledTrueOrderByIdDesc(PageRequest.of(page, size));
        return matchUserWithFreeAreaAndPrivateArea(userPage);
    }

    private Page<UserDTO> matchUserWithFreeAreaAndPrivateArea(Page<User> userPage
    ) {
        return userPage.map(user -> {
            UserDTO userDTO = userMapper.toDTO(user);
            FreeAreaDTO freeAreaDTO = freeAreaClientRest.findById(user.getIdFreeArea());
            PrivateAreaDTO privateAreaDTO = privateAreaClientRest.findById(user.getIdPrivateArea());
            userDTO.setRoles(user.getRoles());
            userDTO.setFreeAreaDTO(freeAreaDTO);
            userDTO.setPrivateAreaDTO(privateAreaDTO);
            userDTO.setIdFreeArea(freeAreaDTO.getId());
            return userDTO;
        });
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        User user = userRepository.findAll().stream().filter(e -> Objects.equals(e.getId(), id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("id: " + id + " does not exist"));
        return getUserDTO(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findEntityById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id: " + id + " does not exist")));
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("email: " + email + " does not exist"));
        return getUserDTO(user);
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .flatMap(this::getUserDTO);
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private Optional<UserDTO> getUserDTO(User user) {
        FreeAreaDTO freeAreaDTO = freeAreaClientRest.findById(user.getIdFreeArea());
        PrivateAreaDTO privateAreaDTO = privateAreaClientRest.findById(user.getIdPrivateArea());
        UserDTO userDTO = userMapper.toDTO(user);
        userDTO.setRoles(user.getRoles());
        userDTO.setFreeAreaDTO(freeAreaDTO);
        userDTO.setPrivateAreaDTO(privateAreaDTO);
        return Optional.of(userDTO);
    }

    @Transactional(readOnly = true)
    public Optional<User> findEntityByUsername(String username) {
        return userRepository.findByUsername(username);
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
            filterListDTO.setRoles(user.getRoles());
            filterListDTO.setFreeAreaDTO(freeAreaDTO);
            filterListDTO.setPrivateAreaDTO(privateAreaDTO);
            filterListDTO.setIdFreeArea(freeAreaDTO.getId());
            return filterListDTO;
        });
    }

    private void validateUserAgeSelected(FilterDTO filterDTO) {
        Long ageFrom = filterDTO.getPreferenceDTO().getAgeFrom();
        Long ageTo = filterDTO.getPreferenceDTO().getAgeTo();

        boolean isAgeFromTooLow = ageFrom < AgeConfiguration.ADULT.getValue();
        boolean isAgeRangeInvalid = ageFrom > ageTo;
        boolean isAgeToTooHigh = ageTo > AgeConfiguration.SENIOR.getValue();

        if (isAgeFromTooLow || isAgeRangeInvalid || isAgeToTooHigh) {
            throw new UserAgeSelectedException("User: " + filterDTO.getUsername()
                    + " has selected an invalid age range: ageFrom = " + ageFrom + ", ageTo = " + ageTo);
        }
    }

    public User save(UserFormDTO userFormDTO) {
        boolean userEmailExists = findByEmailWithoutException(userFormDTO.getEmail());
        if (!userEmailExists) {
            validateUserForm(userFormDTO);
            User newUser = buildUser(userFormDTO);
            return userRepository.save(newUser);
        }
        throw new UsernameRegisteredException("User: " + userFormDTO.getUsername()
                + " is registered");

    }

    public boolean findByEmailWithoutException(String email) {
        Optional<User> user = userRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getEmail(), email))
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
        PrivateAreaDTO newPrivateAreaDTO = createPrivateArea();
        Preference preference = buildPreference(userFormDTO);
        Country country = buildCountry(userFormDTO);
        State state = buildState(userFormDTO, country);
        City city = buildCity(userFormDTO, state);
        List<Role> roles = getRoles(userFormDTO);
        userFormDTO.setRoles(roles);
        return buildUser(userFormDTO, newFreeAreaDTO, newPrivateAreaDTO, preference, country, city, state);
    }

    private List<Role> getRoles(UserFormDTO userFormDTO) {
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        optionalRoleUser.ifPresent(roles::add);

        if (userFormDTO.getAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        return roles;
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
        return countryRepository.findByCountry(userFormDTO.getCountry())
                .orElseGet(() -> {
                    Country newCountry = Country.builder()
                            .country(userFormDTO.getCountry())
                            .build();
                    return countryRepository.save(newCountry);
                });
    }

    private State buildState(UserFormDTO userFormDTO, Country country) {
        return stateRepository.findByStateAndCountry(userFormDTO.getState(), country)
                .orElseGet(() -> {
                    State newState = State.builder()
                            .state(userFormDTO.getState())
                            .country(country)
                            .build();
                    return stateRepository.save(newState);
                });
    }


    private City buildCity(UserFormDTO userFormDTO, State state) {
        return cityRepository.findByCityAndState(userFormDTO.getCity(), state)
                .orElseGet(() -> {
                    City newCity = City.builder()
                            .city(userFormDTO.getCity())
                            .state(state)
                            .build();
                    return cityRepository.save(newCity);
                });
    }


    private User buildUser(UserFormDTO userFormDTO, FreeAreaDTO freeAreaDTO, PrivateAreaDTO privateAreaDTO,
                           Preference preference, Country country, City city, State state) {
        return User.builder()
                .idFreeArea(freeAreaDTO.getId())
                .idPrivateArea(privateAreaDTO.getId())
                .roles(userFormDTO.getRoles())
                .preference(preference)
                .country(country)
                .city(city)
                .state(state)
                .username(userFormDTO.getUsername())
                .age(userFormDTO.getAge())
                .sex(userFormDTO.getSex())
                .email(userFormDTO.getEmail())
                .birthdate(userFormDTO.getBirthdate())
                .registerDate(DateConfiguration.TODAY.getValue())
                .description(userFormDTO.getDescription())
                .isEnabled(UserEnabledConfiguration.IS_ENABLED.getValue())
                .password(passwordEncoder().encode(userFormDTO.getPassword()))
                .admin(userFormDTO.getAdmin())
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
        user.getState().setState(userFormDTO.getState());
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
            throw new UserNotFoundException("User: is not registered");
        }
        userRepository.delete(id);
        freeAreaClientRest.delete(user.get().getIdFreeArea());
        privateAreaClientRest.delete(user.get().getIdPrivateArea());
        return user.get();
    }

    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("username");
        }
        return null;
    }

}
