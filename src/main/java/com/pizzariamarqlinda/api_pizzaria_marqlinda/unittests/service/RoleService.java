package com.pizzariamarqlinda.api_pizzaria_marqlinda.unittests.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectAlreadyExists;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.RoleMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RoleReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RolesResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService  {

    private final RoleRepository repository;
    private final RoleMapper mapper = RoleMapper.INSTANCE;

    public Long save(RoleReqDto role) {
        Role roleConverted = mapper.roleDtoToEntity(role);
        var roleSearched = repository.findByName(role.getName());
        if(roleSearched.isPresent())
            throw new ObjectAlreadyExists("Essa permissão já existe na base de dados.");
        return repository.save(roleConverted).getId();
    }

    public Role findByNameCommonUser(ProfilesUserEnum name){
        var role = repository.findByName(name);
        if(role.isEmpty()){
            var newRole = new Role();
            newRole.setName(ProfilesUserEnum.COMMON_USER);
            return newRole;
        }
        return role.get();
    }

    public List<RolesResDto> all() {
        return repository.findAll().stream()
                .map(mapper::entityToRoleResDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Optional<Role> role = repository.findById(id);
        if(role.isEmpty())
            throw new ObjectAlreadyExists("Usuário inexistente.");
        else
            repository.delete(role.get());
    }

}
