package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Address;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
