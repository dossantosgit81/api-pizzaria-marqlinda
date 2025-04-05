package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.ItemProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemProductRepository extends JpaRepository<ItemProduct, Long> {

    @Query("SELECT ic FROM ItemProduct ic WHERE ic.cart.id = ?1 ")
    List<ItemProduct> findAllByCartId(Long id);

}
