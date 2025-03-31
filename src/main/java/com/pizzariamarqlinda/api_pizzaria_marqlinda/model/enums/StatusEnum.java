package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    ORDER_REFUSED("ORDER_REFUSED"),
    ORDER_AWAITING_SERVICE("ORDER_AWAITING_SERVICE"),
    ORDER_IN_PROGRESS("ORDER_IN_PROGRESS"),
    ORDER_FOR_DELIVERY("ORDER_FOR_DELIVERY"),
    ORDER_OUT_FOR_DELIVERY("ORDER_OUT_FOR_DELIVERY"),
    ORDER_DELIVERED("ORDER_DELIVERED"),
    PROBLEM_WITH_PAYMENT_ORDER("PROBLEM_WITH_PAYMENT_ORDER");

    private final String name;

    /*VAMOS PENSAR PARA FACILITAR EM DEPARATAMENTALIZAÇÃO

    * MOTORCYCLE_COURIER_USER(ORDER_FOR_DELIVERY, ORDER_OUT_FOR_DELIVERY(Adicionando por ele mesmo. Ele não vai poder ver os pedidos saiu para entrega de outras contas)),
    * CHEF_USER(ORDER_AWAITING_SERVICE, ORDER_IN_PROGRESS, ORDER_FOR_DELIVERY),
    * COMMON_USER(Deve ter acesso a lista de seus próprios pedidos),
    * ADMIN_USER(TODOS OS STATUS)
    *
    * Pedidos para entrega
    Pedidos que sairam para entrega
    *
    * */
}
