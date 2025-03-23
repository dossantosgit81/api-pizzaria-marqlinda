package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import java.math.BigDecimal;

public record ItemCartResDto(Long id, Integer quantity, BigDecimal subtotal, ProductResDto product) {
}
