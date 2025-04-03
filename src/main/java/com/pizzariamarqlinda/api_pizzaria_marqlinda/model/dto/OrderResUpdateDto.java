package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.StatusOrderEnum;

public record OrderResUpdateDto(Long id, StatusOrderEnum status, UserOrderResDto attendant) {
}
