package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Set;


public record UserUpdateRoleReqDto(@NotNull(message = "Campo deve estar preenchido") @Valid Set<RoleReqDto> roles) {
}
