package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import java.math.BigDecimal;

public record ItemProductResDto(Long id, Integer quantity, BigDecimal subtotal, ProductResDto product) {
}
