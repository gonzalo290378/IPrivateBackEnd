package com.ms_users.data;

import com.ms_users.dto.CityDTO;
import com.ms_users.dto.CountryDTO;
import com.ms_users.dto.PreferenceDTO;
import com.ms_users.dto.UserDTO;
import com.ms_users.mapper.UserMapper;
import com.ms_users.models.*;
import com.ms_users.models.entity.City;
import com.ms_users.models.entity.Country;
import com.ms_users.models.entity.Preference;
import com.ms_users.models.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public final class Data {

    public Data(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    public final UserMapper userMapper;

    public final static Preference PREFERENCE = Preference.builder()
            .id(Long.valueOf(5L))
            .ageFrom(Long.valueOf(25L))
            .ageTo(Long.valueOf(35L))
            .sexPreference("F")
            .build();

    public final static Country COUNTRY = Country.builder()
            .id(Long.valueOf(1L))
            .country("Argentina")
            .build();

    public final static City CITY = City.builder()
            .id(Long.valueOf(5L))
            .city("Mar del Plata")
            .build();

    public final static List<PrincipalPhotoDTO> PRINCIPAL_PHOTO_DTO = Arrays.asList(PrincipalPhotoDTO.builder()
            .id(Long.valueOf(5L))
            .url("http://www.google.com")
            .build());

    public final static List<PublicContentDTO> PUBLIC_CONTENT_DTO = Arrays.asList(PublicContentDTO.builder()
            .id(Long.valueOf(5L))
            .date(LocalDate.now())
            .description("Foto Publica")
            .contentUrl("Contenido publico")
            .like(100L)
            .build());

    public final static FreeAreaDTO FREE_AREA_DTO = FreeAreaDTO.builder()
            .id(Long.valueOf(5L))
            .isEnabled(Boolean.valueOf(true))
            .principalPhotoDTO(PRINCIPAL_PHOTO_DTO)
            .publicContentDTO(PUBLIC_CONTENT_DTO)
            .build();

    public final static List<PrivateContentDTO> PRIVATE_CONTENT_DTO = Arrays.asList(PrivateContentDTO.builder()
            .id(Long.valueOf(5L))
            .date(LocalDate.now())
            .description("Foto Privada")
            .contentUrl("Contenido privado")
            .like(100L)
            .build());

    public final static PrivateAreaDTO PRIVATE_AREA_DTO = PrivateAreaDTO.builder()
            .id(Long.valueOf(5L))
            .isEnabled(Boolean.valueOf(true))
            .monthCostPrivateArea(BigDecimal.TEN)
            .privateContentDTO(PRIVATE_CONTENT_DTO)
            .build();

    public final static PreferenceDTO PREFERENCE_DTO = PreferenceDTO.builder()
            .id(Long.valueOf(5L))
            .ageFrom(Long.valueOf(20L))
            .ageTo(Long.valueOf(40L))
            .sexPreference("M")
            .build();


    public final static CountryDTO COUNTRY_DTO = CountryDTO.builder()
            .id(Long.valueOf(1L))
            .country("Argentina")
            .build();

    public final static CityDTO CITY_DTO = CityDTO.builder()
            .id(Long.valueOf(1L))
            .city("Mar del plata")
            .build();

    public final static List<User> USERS = List.of(

            new User(1L, "romina", 35L, "F", "romina@gmail.com", LocalDate.of(1989, 11, 1), LocalDate.of(2024, 12, 23),
                    "Hola soy Romina", true, "12345", 1L, 1L, new Preference(), new Country(), new City()),

            new User(2L, "genaro", 30L, "M", "genaro@gmail.com", LocalDate.of(1993, 5, 15), LocalDate.of(2024, 12, 23),
                    "Hola soy Genaro", true, "12345", 2L, 2L, new Preference(), new Country(), new City()),

            new User(3L, "arri", 28L, "M", "arri@gmail.com", LocalDate.of(1995, 3, 10), LocalDate.of(2024, 12, 23),
                    "Hola soy Arri", true, "12345", 3L, 3L, new Preference(), new Country(), new City()),

            new User(4L, "moreno", 32L, "F", "moreno@gmail.com", LocalDate.of(1991, 7, 20), LocalDate.of(2024, 12, 23),
                    "Hola soy Moreno", true, "12345", 4L, 4L, new Preference(), new Country(), new City()),

            new User(5L, "lucia", 29L, "F", "lucia@gmail.com", LocalDate.of(1994, 2, 8), LocalDate.of(2024, 12, 23),
                    "Hola soy Lucia", true, "12345", 5L, 5L, new Preference(), new Country(), new City())
    );

    public final static User USER = new User(5L, "lucia", 29L, "F", "lucia@gmail.com", LocalDate.of(1994, 2, 8), LocalDate.of(2024, 12, 23),
            "Hola soy Lucia", true, "12345", 5L, 5L, PREFERENCE, COUNTRY, CITY);


    public final static UserDTO USER_MAPPER = new UserDTO(5L, "lucia", 29L, "F", "lucia@gmail.com", LocalDate.of(1994, 2, 8), LocalDate.of(2024, 12, 23),
            "Hola soy Lucia", true, "12345", 5L, 5L, FREE_AREA_DTO, PRIVATE_AREA_DTO, PREFERENCE_DTO, COUNTRY_DTO, CITY_DTO);

}