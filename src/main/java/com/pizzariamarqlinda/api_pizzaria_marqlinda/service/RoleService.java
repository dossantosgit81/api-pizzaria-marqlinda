package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectAlreadyExists;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.RoleMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RoleDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService  {

    private final RoleRepository repository;
    private final RoleMapper mapper = RoleMapper.INSTANCE;

    public Long save(RoleDto role) {
        Role roleConverted = mapper.roleDtoToEntity(role);
        return repository.save(roleConverted).getId();
    }

    public List<RoleDto> all() {
        return repository.findAll().stream()
                .map(mapper::entityToRoleDto).toList();
    }

    public void delete(Long id) {
        Optional<Role> role = repository.findById(id);
        if(role.isEmpty())
            throw new ObjectAlreadyExists("Usuário inexistente.");
        else
            repository.delete(role.get());
    }

}
