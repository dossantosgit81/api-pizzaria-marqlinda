package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderReqDto(String observations,
                          @NotNull(message = "Campo deve estar preenchido. ") Boolean delivery,
                          @Valid @NotNull(message = "Campo deve estar preenchido. ") PaymentMethodReqIdDto paymentMethod,
                          @Valid @NotNull(message = "Campo deve estar preenchido. ") AddressReqIdDto address) {

}

/*
*




*
* */