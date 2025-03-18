package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
