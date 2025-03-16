package com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RoleReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RolesResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    Role roleDtoToEntity(RoleReqDto role);
    RoleReqDto entityToRoleDto(Role role);
    RolesResDto entityToRoleResDto(Role role);
}
