package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductReqDto(String description,
                            String details,
                            BigDecimal price,
                            Boolean available,
                            Boolean highlight) {
}
