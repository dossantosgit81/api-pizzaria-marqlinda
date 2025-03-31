package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

   
    @Query(
    "SELECT o FROM Order o LEFT JOIN o.deliveryMan dm JOIN dm.roles r WHERE ('DELIVERY_MAN_USER' IN (r.name) AND (o.status = 'ORDER_FOR_DELIVERY') "+ 
    " OR (o.status = 'ORDER_OUT_FOR_DELIVERY' and dm.id = :loggedUserId) )"+
       " "
    )
    List<Order> findOrderByLoggedUser(@Param("loggedUserId") Long loggedUserId);
}
