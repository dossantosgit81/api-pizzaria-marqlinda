package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RoleDto;

import java.util.List;

public interface RoleService {
    Long Save (RoleDto  role);
    List<RoleDto> all();
}
