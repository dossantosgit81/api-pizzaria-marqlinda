package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;


public record OrderReqDto(String observations,
                          Boolean delivery,
                          PaymentMethodReqIdDto paymentMethod,
                          AddressReqIdDto address) {

}

/*
*




*
* */