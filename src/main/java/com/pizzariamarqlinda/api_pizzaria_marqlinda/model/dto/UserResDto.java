package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import java.util.List;

public record UserResDto(Long id, String name, String lastName, CartDto cart, String login, List<String> roles) {
}
