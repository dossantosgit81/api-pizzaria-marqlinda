package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import java.util.Set;

public record UserResDto(Long id, String name, String lastName, CartReqDto cart, String email, Set<RoleResDto> roles) {
}
