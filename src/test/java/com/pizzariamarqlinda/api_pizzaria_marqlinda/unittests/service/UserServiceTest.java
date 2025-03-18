package com.pizzariamarqlinda.api_pizzaria_marqlinda.unittests.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectAlreadyExists;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.RoleService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.ValidatorLoggedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<ProfilesUserEnum> profileUserEnumArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    @Mock
    private RoleService roleService;

    @Mock
    private ValidatorLoggedUser validatorLoggedUser;

    UserReqDto newUserSuccess;
    User savedUser;
    User adminUser;
    UserReqDto userReqExistsEmail = new UserReqDto();

    @BeforeEach
    public void setUp(){
        service.setEncoder(new BCryptPasswordEncoder());
        newUserSuccess = new UserReqDto();
        newUserSuccess.setName("Rafael");
        newUserSuccess.setLastName("Mendes");
        newUserSuccess.setPassword("12345");

        userReqExistsEmail = new UserReqDto();
        userReqExistsEmail.setName("Rafael");
        userReqExistsEmail.setLastName("Mendes");
        userReqExistsEmail.setEmail("test@gmail.com");

        savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("saveduser@gmail.com");
        Role roleCommonUser = Role.builder().id(1L).name(ProfilesUserEnum.COMMON_USER).build();
        savedUser.setRoles(Set.of(roleCommonUser));

        adminUser = new User();
        adminUser.setId(1L);
        adminUser.setEmail("useradmin@gmail.com");
        Role roleAdmin = Role.builder().id(2L).name(ProfilesUserEnum.ADMIN_USER).build();
        adminUser.setRoles(Set.of(roleAdmin, roleCommonUser));

    }

    @Test
    public void shouldRegisterUserSuccessfully_WhenSavingUser(){
        var role = Role.builder().id(1L).name(ProfilesUserEnum.COMMON_USER).build();
        doReturn(Optional.empty()).when(repository).findByEmail(stringArgumentCaptor.capture());
        doReturn(role).when(roleService).findByNameCommonUser(profileUserEnumArgumentCaptor.capture());
        doReturn(savedUser).when(repository).save(userArgumentCaptor.capture());
        assertEquals(service.save(newUserSuccess), 1L);

        var userCaptured = userArgumentCaptor.getValue();
        assertEquals(newUserSuccess.getEmail(), userCaptured.getEmail());
    }

    @Test
    public void shouldThrowObjectAlreadyException_WhenSavingUserWithExistingEmail(){
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(savedUser));
        assertThrows(ObjectAlreadyExists.class, ()->{
            service.save(userReqExistsEmail);
        });
        verify(repository).findByEmail(anyString());
    }

    @Test
    public void shouldReturnEmptyList_WhenFetchingAllUsers(){
        doReturn(List.of()).when(repository).findAll();
        var result = service.all();
        assertEquals(0, result.size());
    }

    @Test
    public void shouldReturnFilledList_WhenFetchingAllUsers(){
        List<User> users = new ArrayList<>();
        users.add(savedUser);
        doReturn(users).when(repository).findAll();
        var result = service.all();
        assertEquals(1, result.size());
        assertEquals(UserResDto.class, result.getFirst().getClass());
    }

    @Test
    public void shouldReturnUser_WhenSearchingByEmail(){
        doReturn(Optional.of(savedUser)).when(repository).findByEmail(stringArgumentCaptor.capture());
        var result = service.findByEmail("saveduser@gmail.com");
        var emailParameter = stringArgumentCaptor.getValue();
        assertNotNull(result.getId());
        assertEquals(emailParameter, result.getEmail());
    }

    @Test
    public void shouldThrowObjectNotFoundException_WhenEmailDoesNotExist(){
        doReturn(Optional.empty()).when(repository).findByEmail(stringArgumentCaptor.capture());
        assertThrows(ObjectNotFoundException.class, ()->{
            service.findByEmail("notexistuser@gmail.com");
        });
    }

    @Test
    public void shouldThrowObjectNotFoundException_WhenAdminUserAccessesNonExistentUser(){
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        doReturn(Optional.empty()).when(repository).findById(longArgumentCaptor.capture());
        doReturn(adminUser).when(validatorLoggedUser).loggedUser(token);
        doReturn(true).when(validatorLoggedUser).isUserHasRoleAdmin(userArgumentCaptor.capture());
        assertThrows(ObjectNotFoundException.class, ()->{
            service.findById(1L, token);
        });
    }

    @Test
    public void shouldReturnUser_WhenLoggedInUserIsAnAdministrator(){
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        doReturn(Optional.of(adminUser)).when(repository).findById(longArgumentCaptor.capture());
        doReturn(adminUser).when(validatorLoggedUser).loggedUser(token);
        doReturn(true).when(validatorLoggedUser).isUserHasRoleAdmin(userArgumentCaptor.capture());
        var result = service.findById(1L, token);
        assertNotNull(result.id());
        assertEquals(UserResDto.class, result.getClass());
    }

    @Test
    public void shouldReturnUser_WhenLoggedInUserOwnsRequestedResource(){
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        doReturn(Optional.of(savedUser)).when(repository).findById(longArgumentCaptor.capture());
        doReturn(savedUser).when(validatorLoggedUser).loggedUser(token);
        doReturn(false).when(validatorLoggedUser).isUserHasRoleAdmin(userArgumentCaptor.capture());
        doReturn(true).when(validatorLoggedUser).userIsOwnerResource(userArgumentCaptor.capture(), userArgumentCaptor.capture());
        var result = service.findById(1L, token);
        assertNotNull(result.id());
        assertEquals(UserResDto.class, result.getClass());
    }

    @Test
    public void shouldReturnAccessDenied_WhenUserTriesToAccessResourceThatIsNotTheirsAndDoesNotExist(){
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        doReturn(Optional.empty()).when(repository).findById(longArgumentCaptor.capture());
        doReturn(savedUser).when(validatorLoggedUser).loggedUser(token);
        doReturn(false).when(validatorLoggedUser).isUserHasRoleAdmin(userArgumentCaptor.capture());
        assertThrows(AccessDeniedException.class, ()->{
            service.findById(1L, token);
        });
    }

    @Test
    public void shouldReturnAccessDenied_WhenUserTriesToAccessResourceThatIsNotTheirs(){
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        doReturn(Optional.of(savedUser)).when(repository).findById(longArgumentCaptor.capture());
        doReturn(User.builder().id(2L).build()).when(validatorLoggedUser).loggedUser(token);
        doReturn(false).when(validatorLoggedUser).isUserHasRoleAdmin(userArgumentCaptor.capture());
        assertThrows(AccessDeniedException.class, ()->{
            service.findById(1L, token);
        });
    }

}
