package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.validation.constraints.NotNull;

public record AddressReqIdDto(@NotNull(message = "Campo deve estar preenchido.") Long id) {
}
