package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectAlreadyExists;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.UserMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String NON_EXISTENT_USER = "Usuário inexistente.";
    private static final String EXISTS_EMAIL = "Já existe um usuário com esse email.";

    private final ValidatorLoggedUser validatorLoggedUser;
    private final UserMapper mapper = UserMapper.INSTANCE;

    private final UserRepository repository;
    private final RoleService roleService;
    @Setter
    @Autowired
    private PasswordEncoder encoder;

    public Long save(UserReqDto user) {
        this.validateLoginSaved(user.getEmail());
        User userConverted = convertedUser(user);
        User savedUser = repository.save(userConverted);
        return savedUser.getId();
    }

    private User convertedUser(UserReqDto userReq) {
        User userConverted = mapper.userReqDtoToEntity(userReq);
        String password = userReq.getPassword();
        userConverted.setPassword(encoder.encode(password));
        userConverted.setRoles(Set.of(getRoleCommonUser()));
        userConverted.setCart(new Cart());
        return userConverted;
    }

    private Role getRoleCommonUser(){
        return roleService.findByNameCommonUser(ProfilesUserEnum.COMMON_USER);
    }

    private void validateLoginSaved(String email) {
        if (repository.findByEmail(email).isPresent())
            throw new ObjectAlreadyExists(EXISTS_EMAIL);
    }

    public List<UserResDto> all() {
        List<User> users = repository.findAll();
        if(users.isEmpty())
            return List.of();
        return users.stream()
                .map(mapper::entityToUserResDto)
                .collect(Collectors.toList());
    }

    public User findByEmail(String email) {
        var user = repository.findByEmail(email);
        if(user.isEmpty())
            throw new ObjectNotFoundException(NON_EXISTENT_USER);
        return user.get();
    }


    public UserResDto findById(Long id, JwtAuthenticationToken token) {
        Optional<User> userReqSearched = repository.findById(id);
        var loggedUser = validatorLoggedUser.loggedUser(token);
                                                                    /*Se um usuário comum estiver tentando acessar
                                                                    um recurso que não é dele, a gente já recusa de cara por que
                                                                    por definição ele não pode fazer este tipo de operação.
                                                                    --Validação de segurança aqui*/
        if(validatorLoggedUser.isUserContainsValidRole(loggedUser) || (userReqSearched.isPresent() && validatorLoggedUser.userIsOwnerResource(loggedUser, userReqSearched.get())))
            if(userReqSearched.isPresent())
                return mapper.entityToUserResDto(userReqSearched.get());
            else
                throw new ObjectNotFoundException(NON_EXISTENT_USER);
        throw new AccessDeniedException("Acesso negado.");
    }


}
