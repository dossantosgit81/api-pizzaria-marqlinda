package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginReqDto(@NotBlank(message = "Campo deve estar preenchido.") String email, @NotBlank(message = "Campo deve estar preenchido.") String password) {
}
