package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReqDto {
    private String name;
    private String lastName;
    private String phone;
}
