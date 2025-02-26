package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectAlreadyExists;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

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
    public void mustRegisterUserSuccessfully(){
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(savedUser);
        assertEquals(service.save(newUserSuccess), 1L);
        verify(repository).save(any(User.class));
    }

    @Test
    public void mustReturnErrorObjectAlreadyExists(){
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(savedUser));
        assertThrows(ObjectAlreadyExists.class, ()->{
            service.save(userReqExistsEmail);
        });
        verify(repository).findByEmail(anyString());
    }
}
