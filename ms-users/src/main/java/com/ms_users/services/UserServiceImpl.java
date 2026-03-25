package com.ms_users.services;

import com.ms_users.clients.FreeAreaClientRest;
import com.ms_users.clients.PrivateAreaClientRest;
import com.ms_users.dto.*;
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
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Value("${freearea.internal-token}")
    private String SECRET_KEY_FREE_AREA;

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
            userDTO.setFreeAreaDTO(freeAreaDTO);
            userDTO.setIdFreeArea(freeAreaDTO.getId());
            return userDTO;
        });
    }

    @Transactional(readOnly = true)
    public Optional<UserProfileDTO> findById(Long id) {
        User user = userRepository.findAll().stream().filter(e -> Objects.equals(e.getId(), id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("id: " + id + " does not exist"));
        return getUserProfileDTO(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findEntityById(Long id) {
        return Optional.of(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id: " + id + " does not exist")));
    }

    @Transactional(readOnly = true)
    public Optional<UserProfileDTO> findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("email: " + email + " does not exist"));
        return getUserProfileDTO(user);
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        User user = userRepository.findAll().stream().filter(e -> Objects.equals(e.getUsername(), username))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("username: " + username + " does not exist"));
        return getUserDTO(user);
    }

    private Optional<UserDTO> getUserDTO(User user) {
        FreeAreaDTO freeAreaDTO = freeAreaClientRest.findById(user.getIdFreeArea());
        UserDTO userDTO = userMapper.toDTO(user);
        userDTO.setFreeAreaDTO(freeAreaDTO);
        return Optional.of(userDTO);
    }

    private Optional<UserProfileDTO> getUserProfileDTO(User user) {
        Long id = user.getId();
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        List<Role> roles = user.getRoles();
        UserProfileDTO userProfileDTO = UserProfileDTO.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .roles(roles)
                .build();
        return Optional.of(userProfileDTO);
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
            filterListDTO.setRoles(user.getRoles());
            filterListDTO.setFreeAreaDTO(freeAreaDTO);
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

    @Transactional()
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

    @Transactional(readOnly = true)
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
        Country country = buildCountry(userFormDTO);
        State state = buildState(userFormDTO, country);
        City city = buildCity(userFormDTO, state);
        Preference preference = buildPreference(userFormDTO, country, state, city);
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

    @Transactional()
    public FreeAreaDTO createFreeArea() {
        return freeAreaClientRest.save(AreaConfiguration.ENABLED.getValue(), SECRET_KEY_FREE_AREA);
    }

    @Transactional()
    private PrivateAreaDTO createPrivateArea() {
        return privateAreaClientRest.save(AreaConfiguration.DISABLED.getValue(), SECRET_KEY_FREE_AREA);
    }

    private Preference buildPreference(UserFormDTO userFormDTO, Country country, State state, City city) {
        return Preference.builder()
                .ageFrom(userFormDTO.getAgeFrom())
                .ageTo(userFormDTO.getAgeTo())
                .sexPreference(userFormDTO.getSexPreference())
                .filterCountry(country)
                .filterState(state)
                .filterCity(city)
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


    public User updateUserDetails(String username, UserDetailsFreeAreaDTO userDetailsFreeAreaDTO) {
        Optional<User> userEdited = findEntityByUsername(username);
        if (userEdited.isPresent()) {
            userEdited.get().setBirthdate(userDetailsFreeAreaDTO.getBirthdate());
            userEdited.get().setDescription(userDetailsFreeAreaDTO.getDescription());
            Country country = countryRepository.findByCountry(userDetailsFreeAreaDTO.getCountry())
                    .orElseGet(() -> {
                        Country newCountry = Country.builder()
                                .country(userDetailsFreeAreaDTO.getCountry())
                                .build();
                        return countryRepository.save(newCountry);
                    });
            userEdited.get().setCountry(country);

            City city = cityRepository.findByCityAndState(userDetailsFreeAreaDTO.getCity(), userEdited.get().getState())
                    .orElseGet(() -> {
                        City newCity = City.builder()
                                .city(userDetailsFreeAreaDTO.getCity())
                                .state(userEdited.get().getState())
                                .build();
                        return cityRepository.save(newCity);
                    });
            userEdited.get().setCity(city);

            State state = stateRepository.findByStateAndCountry(userDetailsFreeAreaDTO.getState(), country)
                    .orElseGet(() -> {
                        State newState = State.builder()
                                .state(userDetailsFreeAreaDTO.getState())
                                .country(country)
                                .build();
                        return stateRepository.save(newState);
                    });
            userEdited.get().setState(state);
            userEdited.get().setSex(userDetailsFreeAreaDTO.getSex());

        }

        return userEdited
                .map(userRepository::save)
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found"));

    }

    @Override
    @Transactional
    public void updatePreferences(PreferenceDTO preferenceDTO) {
        String username = getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));

        Preference preference = user.getPreference();
        if (preference == null) {
            preference = new Preference();
            user.setPreference(preference);
        }

        preference.setAgeFrom(preferenceDTO.getAgeFrom());
        preference.setAgeTo(preferenceDTO.getAgeTo());
        preference.setSexPreference(preferenceDTO.getSexPreference());

        preference.setFilterCity(null);
        preference.setFilterState(null);
        preference.setFilterCountry(null);
        userRepository.save(user);

        if (preferenceDTO.getFilterCountry() != null && preferenceDTO.getFilterCountry().getCountry() != null) {
            Country country = countryRepository.findByCountry(preferenceDTO.getFilterCountry().getCountry())
                    .orElseGet(() -> countryRepository.save(
                            Country.builder().country(preferenceDTO.getFilterCountry().getCountry()).build()
                    ));
            preference.setFilterCountry(country);
        }

        if (preferenceDTO.getFilterState() != null && preferenceDTO.getFilterState().getState() != null
                && preference.getFilterCountry() != null) {
            Country country = preference.getFilterCountry();
            State state = stateRepository.findByStateAndCountry(preferenceDTO.getFilterState().getState(), country)
                    .orElseGet(() -> stateRepository.save(
                            State.builder().state(preferenceDTO.getFilterState().getState()).country(country).build()
                    ));
            preference.setFilterState(state);
        }

        if (preferenceDTO.getFilterCity() != null && preferenceDTO.getFilterCity().getCity() != null
                && preference.getFilterState() != null) {
            State state = preference.getFilterState();
            City city = cityRepository.findByCityAndState(preferenceDTO.getFilterCity().getCity(), state)
                    .orElseGet(() -> cityRepository.save(
                            City.builder().city(preferenceDTO.getFilterCity().getCity()).state(state).build()
                    ));
            preference.setFilterCity(city);
        }

        userRepository.save(user);
    }


    @Override
    public PreferenceDTO getPreferences() {
        String username = getAuthenticatedUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Preference pref = user.getPreference();

        if (pref == null) {
            return new PreferenceDTO(); // vacío
        }

        return PreferenceDTO.builder()
                .ageFrom(pref.getAgeFrom())
                .ageTo(pref.getAgeTo())
                .sexPreference(pref.getSexPreference())
                .filterCountry(pref.getFilterCountry())
                .filterState(pref.getFilterState())
                .filterCity(pref.getFilterCity())
                .build();
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
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User is not registered");
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String token = null;
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            token = request.getHeader("Authorization");
        }

        if (token != null) {
            freeAreaClientRest.logicalDelete(user.get().getIdFreeArea(), token);
        } else {
            throw new RuntimeException("Token not found");
        }

        freeAreaClientRest.logicalDelete(user.get().getIdFreeArea(), token);
        privateAreaClientRest.logicalDelete(user.get().getIdPrivateArea(), token);
        userRepository.logicDelete(id);
    }

    @Transactional
    public void reactivateUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        String token = null;
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            token = request.getHeader("Authorization");
        }

        if (token != null && user.isPresent()) {
            freeAreaClientRest.reactivateUser(user.get().getIdFreeArea(), token);
        } else {
            throw new RuntimeException("Token not found");
        }

        user.get().setIsEnabled(true);
        userRepository.save(user.get());
    }


    @Transactional(readOnly = true)
    public String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("username");
        }
        return null;
    }

}