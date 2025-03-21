package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryReqDto(@NotBlank(message = "Camo deve estar preenchido.") String name) {
}
