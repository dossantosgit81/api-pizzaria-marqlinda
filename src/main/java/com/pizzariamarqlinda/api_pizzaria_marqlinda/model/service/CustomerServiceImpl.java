package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Customer;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.mapper.CustomerMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper mapper = CustomerMapper.INSTANCE;
    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long save(CustomerReqDto customer) {
        Customer obj = mapper.customerDtoToEntity(customer);
        return repository.save(obj).getId();
    }

    @Override
    public List<CustomerResDto> all() {
       return repository.findAll().stream()
                .map(mapper::entityToCustomerResDto)
                .toList();
    }
}
