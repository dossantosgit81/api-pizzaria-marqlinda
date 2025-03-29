package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.StatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResDto(Long id, StatusEnum status, BigDecimal total, LocalDateTime deliveryForecast, AddressResDto address) {
}
