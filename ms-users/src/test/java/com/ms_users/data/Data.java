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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public final class Data {

    public Data(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public final UserMapper userMapper;

    public final static Integer PAGE = 0;

    public final static Integer SIZE = 10;

    public final static Preference PREFERENCE = Preference.builder()
            .id(5L)
            .ageFrom(25L)
            .ageTo(35L)
            .sexPreference("F")
            .build();

    public final static Country COUNTRY = Country.builder()
            .id(1L)
            .country("Argentina")
            .build();

    public final static City CITY = City.builder()
            .id(1L)
            .city("Mar del Plata")
            .build();

    public final static List<PrincipalPhotoDTO> PRINCIPAL_PHOTO_DTO = Arrays.asList(PrincipalPhotoDTO.builder()
            .id(1L)
            .url("http://www.google.com")
            .build());

    public final static List<PublicContentDTO> PUBLIC_CONTENT_DTO = Arrays.asList(PublicContentDTO.builder()
            .id(1L)
            .date(LocalDate.now())
            .description("Foto Publica")
            .contentUrl("Contenido publico")
            .like(100L)
            .build());


    public final static List<FreeAreaDTO> FREE_AREA_DTO_LIST = List.of(
    new FreeAreaDTO(1L, true, PRINCIPAL_PHOTO_DTO, PUBLIC_CONTENT_DTO),
    new FreeAreaDTO(2L, true, PRINCIPAL_PHOTO_DTO, PUBLIC_CONTENT_DTO),
    new FreeAreaDTO(3L, true, PRINCIPAL_PHOTO_DTO, PUBLIC_CONTENT_DTO),
    new FreeAreaDTO(4L, true, PRINCIPAL_PHOTO_DTO, PUBLIC_CONTENT_DTO),
    new FreeAreaDTO(5L, true, PRINCIPAL_PHOTO_DTO, PUBLIC_CONTENT_DTO)

            );

    public final static FreeAreaDTO FREE_AREA_DTO_1 = FreeAreaDTO.builder()
            .id(1L)
            .isEnabled(Boolean.TRUE)
            .principalPhotoDTO(PRINCIPAL_PHOTO_DTO)
            .publicContentDTO(PUBLIC_CONTENT_DTO)
            .build();

    public final static FreeAreaDTO FREE_AREA_DTO_2 = FreeAreaDTO.builder()
            .id(2L)
            .isEnabled(Boolean.TRUE)
            .principalPhotoDTO(PRINCIPAL_PHOTO_DTO)
            .publicContentDTO(PUBLIC_CONTENT_DTO)
            .build();

    public final static FreeAreaDTO FREE_AREA_DTO_3 = FreeAreaDTO.builder()
            .id(3L)
            .isEnabled(Boolean.TRUE)
            .principalPhotoDTO(PRINCIPAL_PHOTO_DTO)
            .publicContentDTO(PUBLIC_CONTENT_DTO)
            .build();

    public final static FreeAreaDTO FREE_AREA_DTO_4 = FreeAreaDTO.builder()
            .id(4L)
            .isEnabled(Boolean.TRUE)
            .principalPhotoDTO(PRINCIPAL_PHOTO_DTO)
            .publicContentDTO(PUBLIC_CONTENT_DTO)
            .build();

    public final static FreeAreaDTO FREE_AREA_DTO_5 = FreeAreaDTO.builder()
            .id(5L)
            .isEnabled(Boolean.TRUE)
            .principalPhotoDTO(PRINCIPAL_PHOTO_DTO)
            .publicContentDTO(PUBLIC_CONTENT_DTO)
            .build();

    public final static List<PrivateContentDTO> PRIVATE_CONTENT_DTO = Arrays.asList(PrivateContentDTO.builder()
            .id(1L)
            .date(LocalDate.now())
            .description("Foto Privada")
            .contentUrl("Contenido privado")
            .like(100L)
            .build());



    public final static PrivateAreaDTO PRIVATE_AREA_DTO_1 = PrivateAreaDTO.builder()
            .id(1L)
            .isEnabled(Boolean.TRUE)
            .monthCostPrivateArea(BigDecimal.TEN)
            .privateContentDTO(PRIVATE_CONTENT_DTO)
            .build();

    public final static PrivateAreaDTO PRIVATE_AREA_DTO_2 = PrivateAreaDTO.builder()
            .id(2L)
            .isEnabled(Boolean.TRUE)
            .monthCostPrivateArea(BigDecimal.TEN)
            .privateContentDTO(PRIVATE_CONTENT_DTO)
            .build();

    public final static PrivateAreaDTO PRIVATE_AREA_DTO_3 = PrivateAreaDTO.builder()
            .id(3L)
            .isEnabled(Boolean.TRUE)
            .monthCostPrivateArea(BigDecimal.TEN)
            .privateContentDTO(PRIVATE_CONTENT_DTO)
            .build();

    public final static PrivateAreaDTO PRIVATE_AREA_DTO_4 = PrivateAreaDTO.builder()
            .id(4L)
            .isEnabled(Boolean.TRUE)
            .monthCostPrivateArea(BigDecimal.TEN)
            .privateContentDTO(PRIVATE_CONTENT_DTO)
            .build();

    public final static PrivateAreaDTO PRIVATE_AREA_DTO_5 = PrivateAreaDTO.builder()
            .id(5L)
            .isEnabled(Boolean.TRUE)
            .monthCostPrivateArea(BigDecimal.TEN)
            .privateContentDTO(PRIVATE_CONTENT_DTO)
            .build();


    public final static List<PrivateAreaDTO> PRIVATE_AREA_DTO_LIST = List.of(
            new PrivateAreaDTO(1L, true, BigDecimal.TEN, PRIVATE_CONTENT_DTO),
            new PrivateAreaDTO(2L, true, BigDecimal.TEN, PRIVATE_CONTENT_DTO),
            new PrivateAreaDTO(3L, true, BigDecimal.TEN, PRIVATE_CONTENT_DTO),
            new PrivateAreaDTO(4L, true, BigDecimal.TEN, PRIVATE_CONTENT_DTO),
            new PrivateAreaDTO(5L, true, BigDecimal.TEN, PRIVATE_CONTENT_DTO)

    );

    public final static PreferenceDTO PREFERENCE_DTO_1 = PreferenceDTO.builder()
            .id(1L)
            .ageFrom(20L)
            .ageTo(40L)
            .sexPreference("M")
            .build();

    public final static PreferenceDTO PREFERENCE_DTO_2 = PreferenceDTO.builder()
            .id(2L)
            .ageFrom(20L)
            .ageTo(40L)
            .sexPreference("M")
            .build();

    public final static PreferenceDTO PREFERENCE_DTO_3 = PreferenceDTO.builder()
            .id(3L)
            .ageFrom(20L)
            .ageTo(40L)
            .sexPreference("M")
            .build();

    public final static PreferenceDTO PREFERENCE_DTO_4 = PreferenceDTO.builder()
            .id(4L)
            .ageFrom(20L)
            .ageTo(40L)
            .sexPreference("M")
            .build();

    public final static PreferenceDTO PREFERENCE_DTO_5 = PreferenceDTO.builder()
            .id(5L)
            .ageFrom(20L)
            .ageTo(40L)
            .sexPreference("M")
            .build();


    public final static CountryDTO COUNTRY_DTO_1 = CountryDTO.builder()
            .id(1L)
            .country("Argentina")
            .build();

    public final static CountryDTO COUNTRY_DTO_2 = CountryDTO.builder()
            .id(2L)
            .country("Argentina")
            .build();

    public final static CountryDTO COUNTRY_DTO_3 = CountryDTO.builder()
            .id(3L)
            .country("Argentina")
            .build();

    public final static CountryDTO COUNTRY_DTO_4 = CountryDTO.builder()
            .id(4L)
            .country("Argentina")
            .build();

    public final static CountryDTO COUNTRY_DTO_5 = CountryDTO.builder()
            .id(5L)
            .country("Argentina")
            .build();

    public final static CityDTO CITY_DTO_1 = CityDTO.builder()
            .id(1L)
            .city("Mar del plata")
            .build();

    public final static CityDTO CITY_DTO_2 = CityDTO.builder()
            .id(2L)
            .city("Mar del plata")
            .build();

    public final static CityDTO CITY_DTO_3 = CityDTO.builder()
            .id(3L)
            .city("Mar del plata")
            .build();

    public final static CityDTO CITY_DTO_4 = CityDTO.builder()
            .id(4L)
            .city("Mar del plata")
            .build();

    public final static CityDTO CITY_DTO_5 = CityDTO.builder()
            .id(5L)
            .city("Mar del plata")
            .build();

    public final static List<User> USER_LIST = List.of(

            new User(1L, "romina", 35L, "F", "romina@gmail.com", LocalDate.of(1989, 11, 1), LocalDate.of(2024, 12, 23),
                    "Hola soy Romina", true, "12345", 1L, 1L, PREFERENCE, COUNTRY, CITY),

            new User(2L, "genaro", 30L, "M", "genaro@gmail.com", LocalDate.of(1993, 5, 15), LocalDate.of(2024, 12, 23),
                    "Hola soy Genaro", true, "12345", 2L, 2L, PREFERENCE, COUNTRY, CITY),

            new User(3L, "arri", 28L, "M", "arri@gmail.com", LocalDate.of(1995, 3, 10), LocalDate.of(2024, 12, 23),
                    "Hola soy Arri", true, "12345", 3L, 3L, PREFERENCE, COUNTRY, CITY),

            new User(4L, "moreno", 32L, "F", "moreno@gmail.com", LocalDate.of(1991, 7, 20), LocalDate.of(2024, 12, 23),
                    "Hola soy Moreno", true, "12345", 4L, 4L, PREFERENCE, COUNTRY, CITY),

            new User(5L, "lucia", 29L, "F", "lucia@gmail.com", LocalDate.of(1994, 2, 8), LocalDate.of(2024, 12, 23),
                    "Hola soy Lucia", true, "12345", 5L, 5L, PREFERENCE, COUNTRY, CITY)
    );

    public final static List<UserDTO> USER_DTO_LIST =
            List.of(

            new UserDTO(1L, "romina", 35L, "F", "romina@gmail.com", LocalDate.of(1989, 11, 1), LocalDate.of(2024, 12, 23),
            "Hola soy Romina", true, "12345", 1L, 1L, FREE_AREA_DTO_1, PRIVATE_AREA_DTO_1, PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1),

            new UserDTO(2L, "genaro", 30L, "M", "genaro@gmail.com", LocalDate.of(1993, 5, 15), LocalDate.of(2024, 12, 23),
                    "Hola soy Genaro", true, "12345", 2L, 2L, FREE_AREA_DTO_2, PRIVATE_AREA_DTO_2, PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1),

            new UserDTO(3L, "arri", 28L, "M", "arri@gmail.com", LocalDate.of(1995, 3, 10), LocalDate.of(2024, 12, 23),
                    "Hola soy Arri", true, "12345", 3L, 3L, FREE_AREA_DTO_3, PRIVATE_AREA_DTO_3, PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1),

            new UserDTO(4L, "moreno", 32L, "F", "moreno@gmail.com", LocalDate.of(1991, 7, 20), LocalDate.of(2024, 12, 23),
                    "Hola soy Moreno", true, "12345", 4L, 4L, FREE_AREA_DTO_4, PRIVATE_AREA_DTO_4, PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1),

            new UserDTO(5L, "lucia", 29L, "F", "lucia@gmail.com", LocalDate.of(1994, 2, 8), LocalDate.of(2024, 12, 23),
           "Hola soy Lucia", true, "12345", 5L, 5L, FREE_AREA_DTO_5, PRIVATE_AREA_DTO_5, PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1)
    );


    public final static User USER_1 = new User(1L, "romina", 35L, "F", "romina@gmail.com", LocalDate.of(1989, 11, 1), LocalDate.of(2024, 12, 23),
            "Hola soy Romina", true, "12345", 1L, 1L, PREFERENCE, COUNTRY, CITY);

    public final static UserDTO USER_DTO_1 = new UserDTO(1L, "romina", 35L, "F", "lucia@gmail.com", LocalDate.of(1989, 11, 1), LocalDate.of(2024, 12, 23),
            "Hola soy Romina", true, "12345", 1L, 1L, new FreeAreaDTO(), new PrivateAreaDTO(), PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1);

    public final static UserDTO USER_DTO_2 = new UserDTO(2L, "genaro", 30L, "F", "genaro@gmail.com", LocalDate.of(1993, 5, 15), LocalDate.of(2024, 12, 23),
            "Hola soy Genaro", true, "12345", 2L, 2L, new FreeAreaDTO(), new PrivateAreaDTO(), PREFERENCE_DTO_2, COUNTRY_DTO_2, CITY_DTO_1);


    public static Page<User> USERS_PAGINATOR(List<User> users, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), users.size());

        List<User> subList = users.subList(start, end);

        return new PageImpl<>(subList, pageRequest, users.size());
    }


}