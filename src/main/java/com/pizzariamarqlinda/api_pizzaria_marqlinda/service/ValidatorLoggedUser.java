package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectSessionExpiredExceptionException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidatorLoggedUser {

    private static final String ACCESS_DENIED = "Acesso negado.";

    private final UserRepository repository;

    protected User loggedUser(JwtAuthenticationToken token){
        Optional<User> userSearched = repository.findByEmail(token.getName());
        if(userSearched.isEmpty())
            throw new ObjectSessionExpiredExceptionException("Token inválido.");
        return userSearched.get();
    }


    protected void validateUser(User loggedUser, User userReq){
        if(!isUserContainsValidRole(loggedUser)){
            if(!loggedUser.getId().equals(userReq.getId()))
                throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    protected boolean isUserContainsValidRole(User loggedUser){
        for (Role role : loggedUser.getRoles()){
            if(role.getDescription().equals(ProfilesUserEnum.ADMIN_USER)){
                return true;
            }
        }
        return false;
    }

}
