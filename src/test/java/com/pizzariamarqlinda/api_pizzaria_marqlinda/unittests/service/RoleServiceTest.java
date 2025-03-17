package com.pizzariamarqlinda.api_pizzaria_marqlinda.unittests.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.RoleMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RoleResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.RoleRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    public void mustReturnARoleFormTheDatabase(){
        Role role = Role.builder().id(1L).name(ProfilesUserEnum.COMMON_USER).build();
        doReturn(Optional.of(role)).when(repository).findByName(captorProfileEnum.capture());
        var result = service.findByNameCommonUser(ProfilesUserEnum.COMMON_USER);
        assertNotNull(result.getId());
        assertEquals(result.getName(), captorProfileEnum.getValue());
    }

    @Test
    public void mustReturnARoleObject(){
        doReturn(Optional.empty()).when(repository).findByName(captorProfileEnum.capture());
        var result = service.findByNameCommonUser(ProfilesUserEnum.COMMON_USER);
        assertNull(result.getId());
        assertEquals(result.getName(), captorProfileEnum.getValue());
    }

    @Test
    public void mustReturnAListEmpty(){
        doReturn(List.of()).when(repository).findAll();
        var result = service.all();
        assertEquals(0, result.size());
    }

    @Test
    public void mustReturnAListRoleFromDatabase(){
        List<Role> roles = new ArrayList<>();
        Role role = new Role(1L, ProfilesUserEnum.COMMON_USER, Set.of());
        roles.add(role);
        doReturn(roles).when(repository).findAll();
        var result = service.all();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(RoleResDto.class, result.getFirst().getClass());
    }
}
