package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Customer;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.repository.CustomerRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.service.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository repository;

    CustomerReqDto newCustomer;
    Customer savedCustomer;

    @BeforeEach
    public void setUp(){
        newCustomer = new CustomerReqDto();
        newCustomer.setName("Rafael");
        newCustomer.setPhone("6165698754");

        savedCustomer = new Customer();
        savedCustomer.setId(1L);

        Mockito.when(repository.save(Mockito.any())).thenReturn(savedCustomer);
    }

    @Test
    public void deveCadastrarCustomerComSucesso(){
        Assertions.assertEquals(service.save(newCustomer), 1L);
        Mockito.verify(repository).save(Mockito.any(Customer.class));
    }
}
