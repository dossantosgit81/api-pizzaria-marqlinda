package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.StatusOrderEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResDto(Long id,
                          StatusOrderEnum status,
                          BigDecimal total,
                          LocalDateTime deliveryForecast,
                          LocalDateTime dateTimeOrder,
                          PaymentMethodDto paymentMethod,
                          AddressResDto address,
                          String observations,
                          UserOrderResDto user,
                          UserOrderResDto deliveryMan,
                          UserOrderResDto attendant) {
}
