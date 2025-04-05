package com.pizzariamarqlinda.api_pizzaria_marqlinda.validation;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserProfileValidation {

    public boolean isChefUser (User user){
       return this.isUserWithValidRole(user, ProfilesUserEnum.CHEF_USER);
    }

    public boolean isDeliveryManUser (User user){
        return this.isUserWithValidRole(user, ProfilesUserEnum.DELIVERY_MAN_USER);
    }

    public boolean isAdminUser (User user){
        return this.isUserWithValidRole(user, ProfilesUserEnum.ADMIN_USER);
    }

    private boolean isUserWithValidRole (User user, ProfilesUserEnum profilesUserEnum){
        Optional<Role> roleSearched = user.getRoles().stream().filter(role -> profilesUserEnum.getName().equals(role.getName().getName())).findFirst();
        return roleSearched.isPresent();
    }
}
