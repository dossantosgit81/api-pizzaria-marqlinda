package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.repository;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
