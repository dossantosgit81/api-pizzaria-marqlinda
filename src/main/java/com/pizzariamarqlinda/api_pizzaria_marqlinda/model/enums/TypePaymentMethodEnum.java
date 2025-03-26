package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypePaymentMethodEnum {
    DEBIT_CARD("DEBIT_CARD"),
    CREDIT_CARD("CREDIT_CARD"),
    PIX("CHEF_USER");

    private final String name;
}
