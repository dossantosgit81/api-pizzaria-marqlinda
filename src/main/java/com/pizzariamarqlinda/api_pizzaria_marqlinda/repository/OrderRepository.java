package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

   
    @Query(
    "SELECT o FROM Order o "+
    "LEFT JOIN o.deliveryMan deliveryMan "+
    "LEFT JOIN o.user customer "+
    "LEFT JOIN deliveryMan.roles rolesdm "+
    "LEFT JOIN o.attendant attendant "+
    "LEFT JOIN attendant.roles rolesa "+
    "WHERE ('DELIVERY_MAN_USER' IN (rolesdm.name) AND (o.status = 'ORDER_FOR_DELIVERY') OR (o.status = 'ORDER_OUT_FOR_DELIVERY' AND deliveryMan.id = :loggedUserId) )"+
    "OR ('CHEF_USER' IN (rolesa.name) AND (o.status IN ('ORDER_AWAITING_SERVICE', 'ORDER_IN_PROGRESS')) ) " +
    "OR (customer.id = :loggedUserId) "+
    "OR ('ADMIN_USER' IN (:rolesLoggedUser) )"
    )
    List<Order> findOrderByLoggedUser(@Param("loggedUserId") Long loggedUserId, List<String> rolesLoggedUser);
}
