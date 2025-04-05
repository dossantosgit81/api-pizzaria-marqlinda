package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfilesUserEnum {
    ADMIN_USER("ADMIN_USER"),
    COMMON_USER("COMMON_USER"),
    CHEF_USER("CHEF_USER"),
    DELIVERY_MAN_USER("DELIVERY_MAN_USER");

    private final String name;
}
