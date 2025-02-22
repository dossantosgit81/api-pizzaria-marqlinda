package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Email(message = "Email inválido.")
    private String email;
    @NotBlank(message = messageError)
    private String password;
}
