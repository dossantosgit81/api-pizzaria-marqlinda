package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductReqDto(
        @NotBlank(message = "Campo description deve estar preenchido.") String description,
        @NotBlank(message = "Campo details deve estar preenchido.") String details,
        @NotNull(message = "Campo price deve estar preenchido.") BigDecimal price,
        Boolean highlight) {
}
