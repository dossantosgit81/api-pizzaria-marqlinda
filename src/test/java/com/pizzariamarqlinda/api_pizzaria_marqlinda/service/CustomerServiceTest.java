package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    UserReqDto newCustomer;
    User savedCustomer;
    AddressDto addressDto;

    @BeforeEach
    public void setUp(){
        newCustomer = new UserReqDto();
        addressDto = new AddressDto(1L, "Barreiras", "ES", "BRASIL", "Natal", 452, "8899898855", "Joaquim almeida", "Endereco 1");

        newCustomer.setName("Rafael");
        newCustomer.setLastName("Mendes");
        newCustomer.setPhone("6165698754");
        newCustomer.setAddresses(List.of(addressDto));

        savedCustomer = new User();
        savedCustomer.setId(1L);

        Mockito.when(repository.save(Mockito.any())).thenReturn(savedCustomer);
    }

    @Test
    public void deveCadastrarCustomerComSucesso(){
        Assertions.assertEquals(service.save(newCustomer), 1L);
        Mockito.verify(repository).save(Mockito.any(User.class));
    }
}
