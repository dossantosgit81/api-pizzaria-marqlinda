package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {
    private String name;
    private String lastName;
    private String phone;
    private String login;
    private String password;
}
