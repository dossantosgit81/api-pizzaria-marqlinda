package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentMethodReqIdDto(@NotNull(message = "Campo deve estar preenchido") Long id) {
}
