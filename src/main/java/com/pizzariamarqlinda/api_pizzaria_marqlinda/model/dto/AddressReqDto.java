package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressReqDto(@NotBlank(message = "Campo deve ser preenchido.") String city,
                            @Size(min = 2, max = 2, message = "Campo deve ter 2 caracteres")
                            @NotBlank(message = "Campo deve ser preenchido.") String state,
                            @NotBlank(message = "Campo deve ser preenchido.") String street,
                            @NotNull(message = "Campo deve ser preenchido.")  Integer number,
                            @NotBlank(message = "Campo deve ser preenchido.") String zipcode,
                            @NotBlank(message = "Campo deve ser preenchido.") String neighborhood,
                            @NotBlank(message = "Campo deve ser preenchido.") String title) {
}
