package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleReqDto {

    @NotNull(message = "Campo deve estar preenchido.")
    private ProfilesUserEnum name;
}
