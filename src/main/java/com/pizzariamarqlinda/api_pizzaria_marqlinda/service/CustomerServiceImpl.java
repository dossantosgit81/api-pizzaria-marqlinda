package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Customer;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.CustomerMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper mapper = CustomerMapper.INSTANCE;
    private final CustomerRepository repository;
    private final CartService cartService;

    public CustomerServiceImpl(CustomerRepository repository, CartService cartService) {
        this.repository = repository;
        this.cartService = cartService;
    }

    @Override
    public Long save(CustomerReqDto customer) {
        Customer objectConverted = mapper.customerReqDtoToEntity(customer);
        Customer savedCustomer = repository.save(objectConverted);
        Cart cart = new Cart();
        cart.setCustomer(savedCustomer);
        cartService.save(cart);
        return savedCustomer.getId();
    }

    @Override
    public List<CustomerResDto> all() {
       return repository.findAll().stream()
                .map(mapper::entityToCustomerResDto)
                .toList();
    }
}
