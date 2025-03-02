package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUser;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleReqDto {

    @NotBlank(message = "Campo deve estar preenchido.")
    private ProfilesUser description;
}
