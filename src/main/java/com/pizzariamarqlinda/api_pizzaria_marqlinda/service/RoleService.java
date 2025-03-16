package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.RoleMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RolesResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService  {

    private final RoleRepository repository;
    private final RoleMapper mapper;

    public Role findByNameCommonUser(ProfilesUserEnum profile){
        var role = repository.findByName(profile);
        if(role.isEmpty()){
            var newRole = new Role();
            newRole.setName(profile);
            return newRole;
        }
        return role.get();
    }

    public List<RolesResDto> all() {
        return repository.findAll().stream()
                .map(mapper::entityToRoleResDto)
                .collect(Collectors.toList());
    }


}
