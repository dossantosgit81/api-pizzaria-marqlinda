package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.BusinessLogicException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectAlreadyExists;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.UserMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.*;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.validation.UserProfileValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String NON_EXISTENT_USER = "Usuário inexistente.";
    private static final String EXISTS_EMAIL = "Já existe um usuário com esse email.";

    private final LoggedUserService loggedUser;
    private final UserMapper mapper = UserMapper.INSTANCE;
    private final UserProfileValidation userProfileValidation;
    private final UserRepository repository;
    private final RoleService roleService;
    @Setter
    private JwtAuthenticationToken token;
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

    public Page<UserResDto> all(Pageable pageable) {
        var users = repository.findAll(pageable);
        return users.map(mapper::entityToUserResDto);
    }

    public User findByEmail(String email) {
        var user = repository.findByEmail(email);
        if(user.isEmpty())
            throw new ObjectNotFoundException(NON_EXISTENT_USER);
        return user.get();
    }

    @Transactional
    public UserResDto updateRolesUserId(String email, UserUpdateRoleReqDto userUpdateRoleReqDto){
        email = URLDecoder.decode(email, StandardCharsets.UTF_8);
        User loggedUser = this.loggedUser.loggedUser(token);
        if(userProfileValidation.isAdminUser(loggedUser)){
            User userSearched = repository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));
            this.verifyUserIsUserRequest(userSearched, loggedUser);
            List<String> rolesExisting = roleService.all().stream().map(obj -> obj.name().getName()).toList();
            List<String> currentRolesUser = userSearched.getRoles().stream().map(obj -> obj.getName().getName()).toList();
            List<ProfilesUserEnum> rolesReq = userUpdateRoleReqDto.roles().stream().map(RoleReqDto::getName).toList();
            var result = this.updateUserWithNewRoles(rolesReq, userSearched);
            return mapper.entityToUserResDto(result);
        }else
            throw new BusinessLogicException("Algo deu errado com sua solicitação. Verifique seu login ou sua senha e tente novamente.");

    }

    private User updateUserWithNewRoles(List<ProfilesUserEnum> rolesReq, User userSearched) {
        Set<Role> newRoles = new TreeSet<>(Comparator.comparing(Role::getId));
        rolesReq.forEach(p -> {
            newRoles.add(roleService.findByName(p));
        });
        if(!newRoles.isEmpty()) {
            userSearched.setRoles(newRoles);
            return repository.save(userSearched);
        }else
            throw new BusinessLogicException("Lista permissões deve está preenchida.");
    }

    private void verifyUserIsUserRequest(User userSearched, User loggedUser) {
        if(loggedUser.getEmail().equals(userSearched.getEmail()))
            throw new BusinessLogicException("Operação não permitida.");
    }

    public UserResDto findById(Long id) {
        Optional<User> userReqSearched = repository.findById(id);
        var loggedUser = this.loggedUser.loggedUser(token);
        if(this.loggedUser.isUserHasRoleAdmin(loggedUser) || (userReqSearched.isPresent() && this.loggedUser.userIsOwnerResource(loggedUser, userReqSearched.get())))
            if(userReqSearched.isPresent())
                return mapper.entityToUserResDto(userReqSearched.get());
            else
                throw new ObjectNotFoundException(NON_EXISTENT_USER);
        throw new AccessDeniedException("Acesso negado.");
    }

    public UserResDto findByEmailWithValidationsPermission(String email) {
        email = URLDecoder.decode(email, StandardCharsets.UTF_8);
        User userReqSearched = findByEmail(email);
        var loggedUser = this.loggedUser.loggedUser(token);
        if(this.loggedUser.isUserHasRoleAdmin(loggedUser) || (this.loggedUser.userIsOwnerResource(loggedUser, userReqSearched)))
            return mapper.entityToUserResDto(userReqSearched);
        else
            throw new AccessDeniedException("Acesso negado.");
    }


}
