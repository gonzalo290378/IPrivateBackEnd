package com.ms_users;

import com.ms_users.clients.FreeAreaClientRest;
import com.ms_users.clients.PrivateAreaClientRest;
import com.ms_users.data.Data;
import com.ms_users.dto.UserDTO;
import com.ms_users.mapper.*;
import com.ms_users.models.entity.User;
import com.ms_users.repositories.UserRepository;
import com.ms_users.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static com.ms_users.data.Data.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest(classes = {User.class})
@ExtendWith(SpringExtension.class)
class MsUsersApplicationTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    PreferenceMapper preferenceMapper = Mappers.getMapper(PreferenceMapper.class);

    @Mock
    CityMapper cityMapper = Mappers.getMapper(CityMapper.class);

    @Mock
    CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);


    @Mock
    private FreeAreaClientRest freeAreaClientRest;

    @Mock
    private PrivateAreaClientRest privateAreaClientRest;

    @Test
    void findByIdTest(){
        //MOCK / GIVEN
        when(userRepository.findAll()).thenReturn(USERS);
        when(userRepository.findById(Long.valueOf(5L))).thenReturn(Optional.of(USER));
        when(freeAreaClientRest.findById(Long.valueOf(5L))).thenReturn(FREE_AREA_DTO);
        when(privateAreaClientRest.findById(Long.valueOf(5L))).thenReturn(PRIVATE_AREA_DTO);

//        when(preferenceMapper.toDTO(PREFERENCE)).thenReturn(PREFERENCE_DTO);
//        when(countryMapper.toDTO(USER.getCountry())).thenReturn(USER_MAPPER.getCountryDTO());
//        when(cityMapper.toDTO(USER.getCity())).thenReturn(USER_MAPPER.getCityDTO());
        when(userMapper.toDTO(USER)).thenReturn(USER_MAPPER);

        //TEST LOGICA DE NEGOCIO / WHEN
        Optional<UserDTO> user = userServiceImpl.findById(Long.valueOf(5L));

        //ASSERTIONS / THEN
        assertTrue(user.isPresent());
        assertEquals(Long.valueOf(5L), user.get().getId());
        verify(userRepository, times(1)).findById(Long.valueOf(5L));

    }

}