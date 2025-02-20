package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Customer;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.CustomerMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper mapper = CustomerMapper.INSTANCE;
    private final CustomerRepository repository;
    private final CartService cartService;
    private final PasswordEncoder encoder;

    @Transactional
    @Override
    public Long save(CustomerReqDto customer) {
        repository.findByLogin(customer.getLogin());
        Customer customerConverted = mapper.customerReqDtoToEntity(customer);
        String password = customer.getPassword();
        customerConverted.setPassword(encoder.encode(password));
        customerConverted.setRoles(List.of("COMMON_USER"));
        customerConverted.setCart(new Cart());
        Customer savedCustomer = repository.save(customerConverted);
        return savedCustomer.getId();
    }

    @Override
    public List<CustomerResDto> all() {
       return repository.findAll().stream()
                .map(mapper::entityToCustomerResDto)
                .toList();
    }
}
