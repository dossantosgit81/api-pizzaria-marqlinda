package com.pizzariamarqlinda.api_pizzaria_marqlinda.unittests.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectAlreadyExists;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.RoleService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<ProfilesUserEnum> profileUserEnumArgumentCaptor;

    @Mock
    private RoleService roleService;

    UserReqDto newUserSuccess;
    User savedUser;
    UserReqDto userReqExistsEmail = new UserReqDto();

    @BeforeEach
    public void setUp(){
        service.setEncoder(new BCryptPasswordEncoder());
        newUserSuccess = new UserReqDto();
        newUserSuccess.setName("Rafael");
        newUserSuccess.setLastName("Mendes");
        newUserSuccess.setPassword("12345");

        userReqExistsEmail = new UserReqDto();
        userReqExistsEmail.setName("Rafael");
        userReqExistsEmail.setLastName("Mendes");
        userReqExistsEmail.setEmail("test@gmail.com");

        savedUser = new User();
        savedUser.setId(1L);

    }

    @Test
    public void shouldRegisterUserSuccessfully(){
        var role = Role.builder().id(1L).name(ProfilesUserEnum.COMMON_USER).build();
        doReturn(Optional.empty()).when(repository).findByEmail(stringArgumentCaptor.capture());
        doReturn(role).when(roleService).findByNameCommonUser(profileUserEnumArgumentCaptor.capture());
        doReturn(savedUser).when(repository).save(userArgumentCaptor.capture());
        assertEquals(service.save(newUserSuccess), 1L);

        var userCaptured = userArgumentCaptor.getValue();
        assertEquals(newUserSuccess.getEmail(), userCaptured.getEmail());
    }

    @Test
    public void shouldReturnErrorObjectAlreadyExists(){
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(savedUser));
        assertThrows(ObjectAlreadyExists.class, ()->{
            service.save(userReqExistsEmail);
        });
        verify(repository).findByEmail(anyString());
    }
}
