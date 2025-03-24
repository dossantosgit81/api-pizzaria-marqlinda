package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.ItemCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemCartRepository extends JpaRepository<ItemCart, Long> {

    @Query("SELECT ic FROM ItemCart ic WHERE ic.cart.id = ?1 ")
    List<ItemCart> findAllByCartId(Long id);

}
