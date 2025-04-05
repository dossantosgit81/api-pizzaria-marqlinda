package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.validation.constraints.NotBlank;

public record PaymentMethodDto(Long id, @NotBlank(message = "Campo deve ser preenchido.") String typePayment) {
}
