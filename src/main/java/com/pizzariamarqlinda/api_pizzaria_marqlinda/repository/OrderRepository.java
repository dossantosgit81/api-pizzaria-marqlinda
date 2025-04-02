package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

   
    @Query(
    "SELECT o FROM Order o "+
    "LEFT JOIN o.deliveryMan deliveryMan "+
    "LEFT JOIN o.user customer "+
    "WHERE (('DELIVERY_MAN_USER' IN (:rolesLoggedUser) AND (o.status = 'ORDER_FOR_DELIVERY')) OR (o.status = 'ORDER_OUT_FOR_DELIVERY' AND deliveryMan.id = :loggedUserId) )"+
    "OR ('CHEF_USER' IN (:rolesLoggedUser) AND (o.status IN ('ORDER_AWAITING_SERVICE', 'ORDER_IN_PROGRESS')) ) " +
    "OR (customer.id = :loggedUserId) "
    )
    Page<Order> findOrderByLoggedUser(@Param("loggedUserId") Long loggedUserId, List<String> rolesLoggedUser, Pageable pageable);
}
