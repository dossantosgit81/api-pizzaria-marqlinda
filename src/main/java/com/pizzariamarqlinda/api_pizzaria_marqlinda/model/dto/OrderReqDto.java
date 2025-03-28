package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderReqDto(@NotBlank(message = "") String observations,
                          @NotNull(message = "Campo deve estar preenchido. ") Boolean delivery,
                          @NotNull(message = "Campo deve estar preenchido. ") PaymentMethodReqIdDto paymentMethod,
                          @NotNull(message = "Campo deve estar preenchido. ") AddressReqIdDto address) {

}

/*
*




*
* */