package com.ms_users.data;

import com.ms_users.dto.*;
import com.ms_users.mapper.UserMapper;
import com.ms_users.models.*;
import com.ms_users.models.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
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

    public final static List<PrincipalPhotoDTO> PRINCIPAL_PHOTO_DTO = Collections.singletonList(PrincipalPhotoDTO.builder()
            .id(1L)
            .url("http://www.google.com")
            .build());

    public final static List<PublicContentDTO> PUBLIC_CONTENT_DTO = Collections.singletonList(PublicContentDTO.builder()
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

    public final static FreeAreaDTO FREE_AREA_DTO_6 = FreeAreaDTO.builder()
            .id(6L)
            .isEnabled(Boolean.TRUE)
            .principalPhotoDTO(PRINCIPAL_PHOTO_DTO)
            .publicContentDTO(PUBLIC_CONTENT_DTO)
            .build();

    public final static List<PrivateContentDTO> PRIVATE_CONTENT_DTO = Collections.singletonList(PrivateContentDTO.builder()
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

    public final static PrivateAreaDTO PRIVATE_AREA_DTO_6 = PrivateAreaDTO.builder()
            .id(6L)
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


    public final static PreferenceDTO PREFERENCE_DTO_TEST_AGE = PreferenceDTO.builder()
            .id(1L)
            .ageFrom(17L)
            .ageTo(40L)
            .sexPreference("M")
            .build();


    public final static PreferenceDTO PREFERENCE_DTO_TEST_AGE_1 = PreferenceDTO.builder()
            .id(1L)
            .ageFrom(40L)
            .ageTo(22L)
            .sexPreference("M")
            .build();


    public final static PreferenceDTO PREFERENCE_DTO_TEST_AGE_3 = PreferenceDTO.builder()
            .id(1L)
            .ageFrom(40L)
            .ageTo(100L)
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

    public final static Role ROLE = Role.builder()
            .id(1L)
            .name("ROLE_USER")
            .build();




    public final static List<User> USER_LIST = List.of(

            new User(1L, List.of(ROLE), "romina", 35L, "F", "romina@gmail.com", LocalDate.of(1989, 11, 1), LocalDate.of(2024, 12, 23),
                    "Hola soy Romina", true, "12345", 1L, 1L, PREFERENCE, COUNTRY, CITY, false),

            new User(2L, List.of(ROLE), "genaro", 30L, "M", "genaro@gmail.com", LocalDate.of(1993, 5, 15), LocalDate.of(2024, 12, 23),
                    "Hola soy Genaro", true, "12345", 2L, 2L, PREFERENCE, COUNTRY, CITY, false),

            new User(3L, List.of(ROLE), "arri", 28L, "M", "arri@gmail.com", LocalDate.of(1995, 3, 10), LocalDate.of(2024, 12, 23),
                    "Hola soy Arri", true, "12345", 3L, 3L, PREFERENCE, COUNTRY, CITY, false),

            new User(4L, List.of(ROLE), "moreno", 32L, "F", "moreno@gmail.com", LocalDate.of(1991, 7, 20), LocalDate.of(2024, 12, 23),
                    "Hola soy Moreno", true, "12345", 4L, 4L, PREFERENCE, COUNTRY, CITY, false),

            new User(5L, List.of(ROLE),"lucia", 29L, "F", "lucia@gmail.com", LocalDate.of(1994, 2, 8), LocalDate.of(2024, 12, 23),
                    "Hola soy Lucia", true, "12345", 5L, 5L, PREFERENCE, COUNTRY, CITY, false)
    );

    public final static List<UserDTO> USER_DTO_LIST =
            List.of(
                    new UserDTO(1L, List.of(ROLE), "romina", 35L, "F", "romina@gmail.com", LocalDate.of(1989, 11, 1), LocalDate.of(2024, 12, 23),
                            "Hola soy Romina", true, "12345", 1L, 1L, FREE_AREA_DTO_1, PRIVATE_AREA_DTO_1, PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1),

                    new UserDTO(2L, List.of(ROLE),"genaro", 30L, "M", "genaro@gmail.com", LocalDate.of(1993, 5, 15), LocalDate.of(2024, 12, 23),
                            "Hola soy Genaro", true, "12345", 2L, 2L, FREE_AREA_DTO_2, PRIVATE_AREA_DTO_2, PREFERENCE_DTO_2, COUNTRY_DTO_2, CITY_DTO_2),

                    new UserDTO(3L, List.of(ROLE),"arri", 28L, "M", "arri@gmail.com", LocalDate.of(1995, 3, 10), LocalDate.of(2024, 12, 23),
                            "Hola soy Arri", true, "12345", 3L, 3L, FREE_AREA_DTO_3, PRIVATE_AREA_DTO_3, PREFERENCE_DTO_3, COUNTRY_DTO_3, CITY_DTO_3),

                    new UserDTO(4L, List.of(ROLE),"moreno", 32L, "F", "moreno@gmail.com", LocalDate.of(1991, 7, 20), LocalDate.of(2024, 12, 23),
                            "Hola soy Moreno", true, "12345", 4L, 4L, FREE_AREA_DTO_4, PRIVATE_AREA_DTO_4, PREFERENCE_DTO_4, COUNTRY_DTO_4, CITY_DTO_4),

                    new UserDTO(5L, List.of(ROLE),"lucia", 29L, "F", "lucia@gmail.com", LocalDate.of(1994, 2, 8), LocalDate.of(2024, 12, 23),
                            "Hola soy Lucia", true, "12345", 5L, 5L, FREE_AREA_DTO_5, PRIVATE_AREA_DTO_5, PREFERENCE_DTO_5, COUNTRY_DTO_5, CITY_DTO_5)
            );


    public final static User USER_1 = new User(1L, List.of(ROLE), "romina", 35L, "F", "romina@gmail.com", LocalDate.of(1989, 11, 1), LocalDate.of(2024, 12, 23),
            "Hola soy Romina", true, "12345", 1L, 1L, PREFERENCE, COUNTRY, CITY, false);


    public final static User USER_2 = new User(2L, List.of(ROLE), "genaro", 30L, "F", "genaro@gmail.com", LocalDate.of(1993, 5, 15), LocalDate.of(2024, 12, 23),
            "Hola soy Genaro", true, "12345", 2L, 2L, PREFERENCE, COUNTRY, CITY, false);


    public final static User USER_3 = new User(3L, List.of(ROLE), "arri", 28L, "M", "arri@gmail.com", LocalDate.of(1995, 3, 10), LocalDate.of(2024, 12, 23),
            "Hola soy Arri", true, "12345", 3L, 3L, PREFERENCE, COUNTRY, CITY, false);


    public final static User USER_4 = new User(4L, List.of(ROLE), "moreno", 32L, "M", "moreno@gmail.com", LocalDate.of(1991, 7, 20), LocalDate.of(2024, 12, 23),
            "Hola soy Moreno", true, "12345", 4L, 4L, PREFERENCE, COUNTRY, CITY, false);


    public final static UserDTO USER_DTO_1 = new UserDTO(1L, List.of(ROLE),"romina", 35L, "F", "lucia@gmail.com", LocalDate.of(1989, 11, 1), LocalDate.of(2024, 12, 23),
            "Hola soy Romina", true, "12345", 1L, 1L, new FreeAreaDTO(), new PrivateAreaDTO(), PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1);


    public final static UserDTO USER_DTO_2 = new UserDTO(2L, List.of(ROLE),"genaro", 30L, "F", "genaro@gmail.com", LocalDate.of(1993, 5, 15), LocalDate.of(2024, 12, 23),
            "Hola soy Genaro", true, "12345", 2L, 2L, new FreeAreaDTO(), new PrivateAreaDTO(), PREFERENCE_DTO_2, COUNTRY_DTO_2, CITY_DTO_1);


    public final static UserDTO USER_DTO_3 = new UserDTO(3L,List.of(ROLE), "arri", 28L, "M", "arri@gmail.com", LocalDate.of(1995, 3, 10), LocalDate.of(2024, 12, 23),
            "Hola soy Arri", true, "12345", 3L, 3L, new FreeAreaDTO(), new PrivateAreaDTO(), PREFERENCE_DTO_3, COUNTRY_DTO_3, CITY_DTO_3);


    public final static UserDTO USER_DTO_4 = new UserDTO(4L,List.of(ROLE), "moreno", 32L, "M", "moreno@gmail.com", LocalDate.of(1991, 7, 20), LocalDate.of(2024, 12, 23),
            "Hola soy Moreno", true, "12345", 4L, 4L, new FreeAreaDTO(), new PrivateAreaDTO(), PREFERENCE_DTO_4, COUNTRY_DTO_4, CITY_DTO_4);


    public static Page<User> USERS_PAGINATOR(List<User> users, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), users.size());
        List<User> subList = users.subList(start, end);
        return new PageImpl<>(subList, pageRequest, users.size());
    }

    public final static FilterDTO FILTER_DTO_1 = new FilterDTO(null, List.of(ROLE), null, null, "F", "romina@gmail.com", null, null,
            null, true, null, null, null, null, PREFERENCE_DTO_1, null, null);

    public final static FilterDTO FILTER_DTO_2 = new FilterDTO(null, List.of(ROLE), null, null, "F", "romina@gmail.com", null, null,
            null, true, null, null, null, null, PREFERENCE_DTO_TEST_AGE, null, null);

    public final static FilterDTO FILTER_DTO_3 = new FilterDTO(null, List.of(ROLE), null, null, "F", "romina@gmail.com", null, null,
            null, true, null, null, null, null, PREFERENCE_DTO_TEST_AGE_1, null, null);

    public final static FilterDTO FILTER_DTO_4 = new FilterDTO(null, List.of(ROLE), null, null, "F", "romina@gmail.com", null, null,
            null, true, null, null, null, null, PREFERENCE_DTO_TEST_AGE_3, null, null);

    public final static List<FilterDTO> USER_DTO_FILTER_LIST =
            List.of(
                    new FilterDTO(1L, List.of(ROLE), "romina", 35L, "F", "romina@gmail.com", LocalDate.of(1989, 11, 1), LocalDate.of(2024, 12, 23),
                            "Hola soy Romina", true, 1L, 1L, FREE_AREA_DTO_1, PRIVATE_AREA_DTO_1, PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1),

                    new FilterDTO(2L, List.of(ROLE), "genaro", 30L, "M", "genaro@gmail.com", LocalDate.of(1993, 5, 15), LocalDate.of(2024, 12, 23),
                            "Hola soy Genaro", true, 2L, 2L, FREE_AREA_DTO_2, PRIVATE_AREA_DTO_2, PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1),

                    new FilterDTO(3L, List.of(ROLE), "arri", 28L, "M", "arri@gmail.com", LocalDate.of(1995, 3, 10), LocalDate.of(2024, 12, 23),
                            "Hola soy Arri", true, 3L, 3L, FREE_AREA_DTO_3, PRIVATE_AREA_DTO_3, PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1),

                    new FilterDTO(4L, List.of(ROLE), "moreno", 32L, "F", "moreno@gmail.com", LocalDate.of(1991, 7, 20), LocalDate.of(2024, 12, 23),
                            "Hola soy Moreno", true, 4L, 4L, FREE_AREA_DTO_4, PRIVATE_AREA_DTO_4, PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1),

                    new FilterDTO(5L, List.of(ROLE), "lucia", 29L, "F", "lucia@gmail.com", LocalDate.of(1994, 2, 8), LocalDate.of(2024, 12, 23),
                            "Hola soy Lucia", true, 5L, 5L, FREE_AREA_DTO_5, PRIVATE_AREA_DTO_5, PREFERENCE_DTO_1, COUNTRY_DTO_1, CITY_DTO_1)
            );


    public final static UserFormDTO NEW_USER_FORM_EXISTED_1 = new UserFormDTO(1L, List.of(ROLE), "romina", 50L, "F", "romina@gmail.com", LocalDate.of(1945, 5, 1), LocalDate.of(2024, 12, 23),
            "Hola soy Romina", true, "12345", 1L, 1L, 25L, 39L, "M", "Argentina", "Mar del Plata", false);


    public final static UserFormDTO NEW_USER_FORM_EXISTED_2 = new UserFormDTO(2L, List.of(ROLE), "gabriela", 50L, "F", "gabriela@gmail.com", LocalDate.of(1945, 6, 1), LocalDate.of(2024, 12, 23),
            "Hola soy Gabriela", true, "12345", 2L, 2L, 39L, 25L, "M", "Argentina", "Mar del Plata", false);

    public final static UserFormDTO NEW_USER_FORM_EXISTED_3 = new UserFormDTO(3L, List.of(ROLE), "julio", 16L, "M", "julio@gmail.com", LocalDate.of(2008, 5, 1), LocalDate.of(2024, 12, 23),
            "Hola soy Julio", true, "12345", 3L, 3L, 18L, 25L, "M", "Argentina", "Mar del Plata", false);

    public final static UserFormDTO NEW_USER_FORM_DTO = new UserFormDTO(1L, List.of(ROLE), "analia", 50L, "F", "analia@gmail.com", LocalDate.of(1945, 3, 1), LocalDate.of(2024, 12, 23),
            "Hola soy Analia", true, "12345", 1L, 1L, 25L, 39L, "M", "Argentina", "Mar del Plata", false);

}