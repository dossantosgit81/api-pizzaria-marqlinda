package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusOrderEnum {
    ORDER_AWAITING_SERVICE("ORDER_AWAITING_SERVICE"),
    ORDER_IN_PROGRESS("ORDER_IN_PROGRESS"),
    ORDER_FOR_DELIVERY("ORDER_FOR_DELIVERY"),
    ORDER_OUT_FOR_DELIVERY("ORDER_OUT_FOR_DELIVERY"),
    ORDER_DELIVERED("ORDER_DELIVERED"),
    PROBLEM_WITH_DELIVERY("PROBLEM_WITH_DELIVERY");

    private final String name;

}
