package com.ms_users;

import com.ms_users.clients.FreeAreaClientRest;
import com.ms_users.clients.PrivateAreaClientRest;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

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
    private FreeAreaClientRest freeAreaClientRest;

    @Mock
    private PrivateAreaClientRest privateAreaClientRest;

    @Test
    void findByIdTest(){
        //MOCK / GIVEN
        when(userRepository.findAll()).thenReturn(USER_LIST);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER_1));
        when(freeAreaClientRest.findById(anyLong())).thenReturn(FREE_AREA_DTO_1);
        when(privateAreaClientRest.findById(anyLong())).thenReturn(PRIVATE_AREA_DTO_1);
        when(userMapper.toDTO(any(User.class))).thenReturn(USER_DTO_1);

        //TEST LOGICA DE NEGOCIO / WHEN
        Optional<UserDTO> user = userServiceImpl.findById(5L);

        //ASSERTIONS / THEN
        assertTrue(user.isPresent());
        assertEquals(Long.valueOf(1L), user.get().getId());
        verify(userRepository).findAll();

    }

    @Test
    void findAllTest(){
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

}

