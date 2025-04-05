package com.pizzariamarqlinda.api_pizzaria_marqlinda.unittests.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectSessionExpiredExceptionException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.LoggedUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class LoggedUserServiceTest {

    @InjectMocks
    private LoggedUserService validatorLoggedUser;

    @Mock
    private UserRepository repository;

    @Captor
    private ArgumentCaptor<Long> argumentCaptorLong;

    @Test
    public void shouldReturnUser_WhenValidIdToken(){
        User user = User.builder().id(1L).build();
        JwtAuthenticationToken token = mock();
        doReturn("1").when(token).getName();
        doReturn(Optional.of(user)).when(repository).findById(argumentCaptorLong.capture());
        var result = validatorLoggedUser.loggedUser(token);
        Assertions.assertEquals(argumentCaptorLong.getValue(), result.getId());
    }

    @Test
    public void shouldReturnObjectSessionExpiredException_WhenInvalidIdToken(){
        User user = User.builder().id(1L).build();
        JwtAuthenticationToken token = mock();
        doReturn("1").when(token).getName();
        doReturn(Optional.empty()).when(repository).findById(argumentCaptorLong.capture());
        Assertions.assertThrows(ObjectSessionExpiredExceptionException.class, ()->{
            validatorLoggedUser.loggedUser(token);
        });
    }

    @Test
    public void shouldReturnTrue_WhenUserIsOwnerResource(){
        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(1L).build();
        var result = validatorLoggedUser.userIsOwnerResource(user1, user2);
        Assertions.assertTrue(result);
    }

    @Test
    public void shouldReturnFalse_WhenUserIsNoOwnerResource(){
        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(2L).build();
        var result = validatorLoggedUser.userIsOwnerResource(user1, user2);
        Assertions.assertFalse(result);
    }

    @Test
    public void shouldReturnTrue_WhenUserHasHoleAdmin(){
        Role roleAdmin = Role.builder().id(1L).name(ProfilesUserEnum.ADMIN_USER).build();
        Role roleCommon = Role.builder().id(2L).name(ProfilesUserEnum.ADMIN_USER).build();
        Role roleChef = Role.builder().id(3L).name(ProfilesUserEnum.ADMIN_USER).build();
        User user = User.builder().roles(Set.of(roleAdmin, roleCommon, roleChef)).build();
        var result = validatorLoggedUser.isUserHasRoleAdmin(user);
        Assertions.assertTrue(result);
    }

    @Test
    public void shouldReturnTrue_WhenUserHasDoesNotHaveAdmin(){
        Role roleCommon = Role.builder().id(1L).name(ProfilesUserEnum.COMMON_USER).build();
        Role roleChef = Role.builder().id(2L).name(ProfilesUserEnum.CHEF_USER).build();
        User user = User.builder().roles(Set.of(roleCommon, roleChef)).build();
        var result = validatorLoggedUser.isUserHasRoleAdmin(user);
        Assertions.assertFalse(result);
    }
}
