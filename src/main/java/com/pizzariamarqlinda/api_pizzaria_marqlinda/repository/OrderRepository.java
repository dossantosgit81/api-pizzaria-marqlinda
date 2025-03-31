package com.pizzariamarqlinda.api_pizzaria_marqlinda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

   
    @Query(
    "SELECT o FROM Order o LEFT JOIN o.deliveryMan dm JOIN o.user u JOIN dm.roles r "+
    "WHERE ('DELIVERY_MAN_USER' IN (r.name) AND (o.status = 'ORDER_FOR_DELIVERY') OR (o.status = 'ORDER_OUT_FOR_DELIVERY' AND dm.id = :loggedUserId) )"+
    "OR ('CHEF_USER' IN (r.name) AND (o.status IN ('ORDER_AWAITING_SERVICE', 'ORDER_IN_PROGRESS')) ) " +
    "OR ('COMMON_USER' IN (r.name) AND u.id = :loggedUserId) "+
    "OR ('ADMIN_USER' IN (r.name) )"
    )
    List<Order> findOrderByLoggedUser(@Param("loggedUserId") Long loggedUserId);
}
