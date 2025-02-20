package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import java.util.List;

public record CustomerResDto(Long id, String name, String lastName, List<AddressDto> address, CartDto cart, String login, List<String> roles) {
}
