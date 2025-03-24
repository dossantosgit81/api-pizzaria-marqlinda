package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResDto(BigDecimal total, List<ItemCartResDto> items) {
}
