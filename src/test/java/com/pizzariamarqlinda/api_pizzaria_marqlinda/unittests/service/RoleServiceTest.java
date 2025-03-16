package com.pizzariamarqlinda.api_pizzaria_marqlinda.unittests.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.RoleMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RolesResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.RoleRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @InjectMocks
    private RoleService service;

    @Mock
    private RoleRepository repository;

    @Mock
    private RoleMapper mapper;

    @Captor
    private ArgumentCaptor<ProfilesUserEnum> captorProfileEnum;

    Role role;

    @Test
    public void mustReturnARoleFormTheDatabase(){
        role = Role.builder().id(1L).name(ProfilesUserEnum.COMMON_USER).build();
        doReturn(Optional.of(role)).when(repository).findByName(captorProfileEnum.capture());
        var result = service.findByNameCommonUser(ProfilesUserEnum.COMMON_USER);
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(result.getName(), captorProfileEnum.getValue());
    }

    @Test
    public void mustReturnARoleObject(){
        doReturn(Optional.empty()).when(repository).findByName(captorProfileEnum.capture());
        var result = service.findByNameCommonUser(ProfilesUserEnum.COMMON_USER);
        Assertions.assertNull(result.getId());
        Assertions.assertEquals(result.getName(), captorProfileEnum.getValue());
    }

    @Test
    public void mustReturnAListEmpty(){
        doReturn(List.of()).when(repository).findAll();
        var result = service.all();
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void mustReturnAListRoleFromDatabase(){
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        doReturn(roles).when(repository).findAll();
        var result = service.all();
        Assertions.assertEquals(1, result.size());
    }
}
