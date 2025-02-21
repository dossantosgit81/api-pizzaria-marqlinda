package com.pizzariamarqlinda.api_pizzaria_marqlinda.service.impl;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.RoleMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RoleDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.RoleRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper = RoleMapper.INSTANCE;

    @Override
    public Long Save(RoleDto role) {
        Role roleConverted = mapper.roleDtoToEntity(role);
        return repository.save(roleConverted).getId();
    }

    @Override
    public List<RoleDto> all() {
        return repository.findAll().stream()
                .map(mapper::entityToRoleDto).toList();
    }

}
