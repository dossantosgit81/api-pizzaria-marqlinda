package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    ORDER_REFUSED("ORDER_REFUSED"),
    ORDER_IN_PROGRESS("ORDER_IN_PROGRESS"),
    ORDER_OUT_FOR_DELIVERY("ORDER_OUT_FOR_DELIVERY"),
    ORDER_DELIVERED("ORDER_DELIVERED");

    private final String name;
}
