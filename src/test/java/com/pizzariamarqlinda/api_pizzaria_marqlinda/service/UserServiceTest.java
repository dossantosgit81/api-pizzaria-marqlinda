package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    UserReqDto newCustomer;
    User savedCustomer;

    @BeforeEach
    public void setUp(){
        service.setEncoder(new BCryptPasswordEncoder());

        newCustomer = new UserReqDto();
        newCustomer.setName("Rafael");
        newCustomer.setLastName("Mendes");
        newCustomer.setPassword("343456");
        newCustomer.setPhone("6165698754");

        savedCustomer = new User();
        savedCustomer.setId(1L);
        savedCustomer.setRoles(List.of("COMMON_USER"));

        when(repository.save(any())).thenReturn(savedCustomer);



    }

    @Test
    public void mustRegisterUserSuccessfully(){

        assertEquals(service.save(newCustomer), 1L);

        verify(repository).save(any(User.class));
    }
}
