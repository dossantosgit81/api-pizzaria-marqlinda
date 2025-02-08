package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import java.util.List;

public record CustomerResDto(Long id, String name, List<AddressDto> address, CartDto cart) {
}
