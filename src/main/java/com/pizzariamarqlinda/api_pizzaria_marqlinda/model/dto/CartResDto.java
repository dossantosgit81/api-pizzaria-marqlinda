package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import java.math.BigDecimal;
import java.util.Set;

public record CartResDto(BigDecimal total, Set<ItemProductResDto> items) {
}
