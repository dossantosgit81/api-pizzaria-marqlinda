package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {
    private final String messageError = "Campo deve estar preenchido.";

    @NotBlank(message = messageError)
    private String name;
    @NotBlank(message = messageError)
    private String lastName;
    @NotBlank(message = messageError)
    private String phone;
    @NotBlank(message = messageError)
    private String login;
    @NotBlank(message = messageError)
    private String password;
}
