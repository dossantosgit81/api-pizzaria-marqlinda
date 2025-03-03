package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectAlreadyExists;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.RoleMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RoleReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
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

    public Long save(RoleReqDto role) {
        Role roleConverted = mapper.roleDtoToEntity(role);
        return repository.save(roleConverted).getId();
    }

    public Role findByName(ProfilesUserEnum name){
        return repository.findByName(name.getName());
    }

    public List<RoleReqDto> all() {
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
