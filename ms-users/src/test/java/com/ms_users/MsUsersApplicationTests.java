package com.ms_users;

import com.ms_users.clients.FreeAreaClientRest;
import com.ms_users.clients.PrivateAreaClientRest;
import com.ms_users.dto.FilterDTO;
import com.ms_users.dto.UserDTO;
import com.ms_users.exceptions.*;
import com.ms_users.mapper.FilterMapper;
import com.ms_users.mapper.UserMapper;
import com.ms_users.models.entity.User;
import com.ms_users.repositories.RoleRepository;
import com.ms_users.repositories.UserRepository;
import com.ms_users.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.ms_users.data.Data.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
//mvn clean verify

@SpringBootTest(classes = {User.class})
@ExtendWith(SpringExtension.class)
class MsUsersApplicationTests {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    FilterMapper filterMapper = Mappers.getMapper(FilterMapper.class);

    @Mock
    private FreeAreaClientRest freeAreaClientRest;

    @Mock
    private PrivateAreaClientRest privateAreaClientRest;

    @Test
    void findByIdTest() {
        //MOCK / GIVEN
        when(userRepository.findAll()).thenReturn(USER_LIST);
        when(freeAreaClientRest.findById(anyLong())).thenReturn(FREE_AREA_DTO_1);
        when(privateAreaClientRest.findById(anyLong())).thenReturn(PRIVATE_AREA_DTO_1);
        when(userMapper.toDTO(any(User.class))).thenReturn(USER_DTO_1);

        //TEST LOGICA DE NEGOCIO / WHEN
        Optional<UserDTO> user = userServiceImpl.findById(5L);

        //ASSERTIONS / THEN
        assertTrue(user.isPresent());
        assertEquals(Long.valueOf(1L), user.get().getId());
        verify(userRepository, times(1)).findAll();
        verify(freeAreaClientRest, times(1)).findById(anyLong());
    }

    @Test
    void findAllTest() {
        //MOCK / GIVEN
        when(userRepository.findByIsEnabledTrueOrderByIdDesc(PageRequest.of(PAGE, SIZE))).thenReturn(USERS_PAGINATOR(USER_LIST, PAGE, SIZE));

        for (int i = 0; i < USER_LIST.size(); i++) {
            when(userMapper.toDTO(USER_LIST.get(i))).thenReturn(USER_DTO_LIST.get(i));
            when(freeAreaClientRest.findById(anyLong())).thenReturn(FREE_AREA_DTO_LIST.get(i));
            when(privateAreaClientRest.findById(anyLong())).thenReturn(PRIVATE_AREA_DTO_LIST.get(i));
        }

        //TEST LOGICA DE NEGOCIO / WHEN
        Page<UserDTO> user = userServiceImpl.findAll(PAGE, SIZE);

        //ASSERTIONS / THEN
        assertFalse(user.isEmpty());
        assertEquals(Long.valueOf(5L), user.getTotalElements());
    }

    @Test
    void findByIdExceptionTest() {
        when(userRepository.findAll()).thenReturn(USER_LIST);
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.findById(999L));
    }

    @Test
    void findEntityByIdTest() {
        //MOCK / GIVEN
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER_2));
        when(freeAreaClientRest.findById(anyLong())).thenReturn(FREE_AREA_DTO_2);
        when(privateAreaClientRest.findById(anyLong())).thenReturn(PRIVATE_AREA_DTO_2);
        when(userMapper.toDTO(any(User.class))).thenReturn(USER_DTO_2);

        //TEST LOGICA DE NEGOCIO / WHEN
        Optional<User> user = userServiceImpl.findEntityById(2L);

        //ASSERTIONS / THEN
        assertTrue(user.isPresent());
        assertEquals(Long.valueOf(2L), user.get().getId());
        verify(userRepository, times(1)).findById(anyLong());

    }

    @Test
    void findByEmailTest() {
        //MOCK / GIVEN
        when(userRepository.findAll()).thenReturn(USER_LIST);
        when(freeAreaClientRest.findById(anyLong())).thenReturn(FREE_AREA_DTO_4);
        when(privateAreaClientRest.findById(anyLong())).thenReturn(PRIVATE_AREA_DTO_4);
        when(userMapper.toDTO(any(User.class))).thenReturn(USER_DTO_4);

        //TEST LOGICA DE NEGOCIO / WHEN
        Optional<UserDTO> user = userServiceImpl.findByEmail("moreno@gmail.com");

        //ASSERTIONS / THEN
        assertTrue(user.isPresent());
        assertEquals(Long.valueOf(4L), user.get().getId());
    }

    @Test
    void findByEmailExceptionTest() {
        when(userRepository.findAll()).thenReturn(USER_LIST);
        assertThrows(EmailNotFoundException.class, () -> userServiceImpl.findByEmail("noexiste@gmail.com"));
    }



    @Test
    void filterTest() {
        //MOCK / GIVEN
        when(userRepository.filter(FILTER_DTO_1, PageRequest.of(PAGE, SIZE))).thenReturn(USERS_PAGINATOR(USER_LIST, PAGE, SIZE));

        for (int i = 0; i < USER_LIST.size(); i++) {
            when(filterMapper.toDTO(USER_LIST.get(i))).thenReturn(USER_DTO_FILTER_LIST.get(i));
            when(freeAreaClientRest.findById(anyLong())).thenReturn(FREE_AREA_DTO_LIST.get(i));
            when(privateAreaClientRest.findById(anyLong())).thenReturn(PRIVATE_AREA_DTO_LIST.get(i));
        }

        //TEST LOGICA DE NEGOCIO / WHEN
        Page<FilterDTO> filterDTOList = userServiceImpl.filter(FILTER_DTO_1, 0, 10);

        //ASSERTIONS / THEN
        assertFalse(filterDTOList.isEmpty());
        assertEquals(Long.valueOf(5L), filterDTOList.getTotalElements());
    }

    @Test
    void filterExceptionTest() {
        //MOCK / GIVEN
        when(userRepository.filter(FILTER_DTO_2, PageRequest.of(PAGE, SIZE))).thenReturn(USERS_PAGINATOR(USER_LIST, PAGE, SIZE));

        for (int i = 0; i < USER_LIST.size(); i++) {
            when(filterMapper.toDTO(USER_LIST.get(i))).thenReturn(USER_DTO_FILTER_LIST.get(i));
            when(freeAreaClientRest.findById(anyLong())).thenReturn(FREE_AREA_DTO_LIST.get(i));
            when(privateAreaClientRest.findById(anyLong())).thenReturn(PRIVATE_AREA_DTO_LIST.get(i));
        }

        //TEST LOGICA DE NEGOCIO / WHEN
        assertThrows(UserAgeSelectedException.class, () -> userServiceImpl.filter(FILTER_DTO_2, 0, 10));
        assertThrows(UserAgeSelectedException.class, () -> userServiceImpl.filter(FILTER_DTO_3, 0, 10));
        assertThrows(UserAgeSelectedException.class, () -> userServiceImpl.filter(FILTER_DTO_4, 0, 10));
    }

    @Test
    void saveTest() {
        //MOCK / GIVEN
        when(userRepository.save(any(User.class))).thenReturn(USER_1);
        when(userRepository.findAll()).thenReturn(USER_LIST);
        when(roleRepository.findByName(anyString())).thenReturn(Optional.ofNullable(ROLE));
        when(freeAreaClientRest.save(true)).thenReturn(FREE_AREA_DTO_6);
        when(privateAreaClientRest.save(false)).thenReturn(PRIVATE_AREA_DTO_6);

        //TEST LOGICA DE NEGOCIO / WHEN
        Optional<User> user = Optional.ofNullable(userServiceImpl.save(NEW_USER_FORM_DTO));

        //ASSERTIONS / THEN
        assertTrue(user.isPresent());
        assertEquals(Long.valueOf(1L), user.get().getId());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).findAll();
        verify(freeAreaClientRest, times(1)).save(true);
        verify(privateAreaClientRest, times(1)).save(false);
    }

    @Test
    void saveUserRegisteredTest() {
        //MOCK / GIVEN
        when(userRepository.save(any(User.class))).thenReturn(USER_1);
        when(userRepository.findAll()).thenReturn(USER_LIST);

        //ASSERTIONS / THEN
        assertThrows(UsernameRegisteredException.class, () -> userServiceImpl.save(NEW_USER_FORM_EXISTED_1));
        assertThrows(InvalidAgeRangeException.class, () -> userServiceImpl.save(NEW_USER_FORM_EXISTED_2));
        assertThrows(InvalidBirthdateException.class, () -> userServiceImpl.save(NEW_USER_FORM_EXISTED_3));
    }

    @Test
    void updateTest() {
        //MOCK / GIVEN
        when(userRepository.save(any(User.class))).thenReturn(USER_1);

        //TEST LOGICA DE NEGOCIO / WHEN
        Optional<User> user = Optional.ofNullable(userServiceImpl.update(NEW_USER_FORM_DTO, USER_1));

        //ASSERTIONS / THEN
        assertTrue(user.isPresent());
        assertEquals(Long.valueOf(1L), user.get().getId());
        verify(userRepository, times(1)).save(any(User.class));
    }

}

