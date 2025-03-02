package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.service.annotation.GetExchange;

@Getter
@AllArgsConstructor
public enum ProfilesUser {
    ADMIN_USER("ADMIN_USER"),
    COMMON_USER("COMMON_USER"),
    CHEF_USER("CHEF_USER");

    private final String name;
}
