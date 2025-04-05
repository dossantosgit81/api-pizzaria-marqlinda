package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.StatusOrderEnum;
import jakarta.validation.constraints.NotNull;

public record OrderReqUpdateDto(@NotNull(message = "Campo deve está preenchido.") StatusOrderEnum status) {
}
