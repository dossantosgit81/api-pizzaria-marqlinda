package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Address;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.id = ?1 and a.user.id = ?2 ")
    Optional<Address> findByUser(Long idAddress, Long idUser);

    @Query("SELECT a FROM Address a WHERE a.user.id = ?1 ")
    List<Address> findALl(Long idUser);
}
