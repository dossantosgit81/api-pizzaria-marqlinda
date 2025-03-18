package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectSessionExpiredExceptionException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidatorLoggedUserService {

    private static final String ACCESS_DENIED = "Acesso negado.";

    private final UserRepository repository;

    public User loggedUser(JwtAuthenticationToken token){
        Optional<User> userSearched = repository.findById(Long.valueOf(token.getName()));
        if(userSearched.isEmpty())
            throw new ObjectSessionExpiredExceptionException("Token inválido.");
        return userSearched.get();
    }

    public boolean userIsOwnerResource(User loggedUser, User userReq){
       return loggedUser.getId().equals(userReq.getId());
    }

    public boolean isUserHasRoleAdmin(User loggedUser){
        for (Role role : loggedUser.getRoles()){
            if(role.getName().equals(ProfilesUserEnum.ADMIN_USER)){
                return true;
            }
        }
        return false;
    }

}
