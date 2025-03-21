
package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 ")
    Page<Product> findByCategory (Long categoryId, Pageable pageable);
}
