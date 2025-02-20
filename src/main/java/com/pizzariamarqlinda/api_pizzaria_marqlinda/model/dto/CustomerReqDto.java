package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReqDto {
    private String name;
    private String lastName;
    private String phone;
    private String login;
    private String password;
}
