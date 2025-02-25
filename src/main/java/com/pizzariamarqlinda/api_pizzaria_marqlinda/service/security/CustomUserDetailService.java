package com.pizzariamarqlinda.api_pizzaria_marqlinda.service.security;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userService.findUserByEmail(email);
        if(user.isEmpty()){
            throw new ObjectNotFoundException("Usuário não encontrado.");
        }
        //TODO não se esqueça de verficar se de fato esse email é do usuário logado
        var useFound = user.get();
        return User.builder()
                .username(useFound.getName())
                .password(useFound.getPassword())
                .roles(useFound.getRoles().toArray(new String[user.get().getRoles().size()]))
                .build();
    }
}
