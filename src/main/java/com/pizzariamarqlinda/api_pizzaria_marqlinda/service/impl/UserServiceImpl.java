package com.pizzariamarqlinda.api_pizzaria_marqlinda.service.impl;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectAlreadyExists;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.UserMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String NON_EXISTENT_USER = "Usuário inexistente.";
    public static final String ACCESS_DENIED = "Acesso negado.";
    public static final String EXISTS_EMAIL = "Já existe um usuário com esse email.";

    private final UserMapper mapper = UserMapper.INSTANCE;
    private final UserRepository repository;
    @Setter
    @Autowired
    private PasswordEncoder encoder;

    @Override
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
        userConverted.setRoles(List.of("COMMON_USER"));
        userConverted.setCart(new Cart());
        return userConverted;
    }

    private void validateLoginSaved(String email) {
        if (repository.findByEmail(email).isPresent())
            throw new ObjectAlreadyExists(EXISTS_EMAIL);
    }

    @Override
    public List<UserResDto> all() {
        return repository.findAll().stream()
                .map(mapper::entityToUserResDto)
                .toList();
    }

    @Override
    public User findByEmail(String email) {
        var user = repository.findByEmail(email);
        if(user.isEmpty())
            throw new ObjectNotFoundException(NON_EXISTENT_USER);
        return user.get();
    }

    @Override
    public UserResDto findById(Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User userReq = findOne(id);
        User userAuth = findByEmail(userDetails.getUsername());
        validUser(userAuth, userReq);
        return mapper.entityToUserResDto(userAuth);
    }

    private void validUser(User userAuth, User userReq){
        //If userRoles contains ()AdminUSER
         if(!userAuth.getId().equals(userReq.getId()))
            throw new AccessDeniedException(ACCESS_DENIED);
    }

    private User findOne(Long id) {
        var user = repository.findById(id);
        if (user.isEmpty())
            throw new ObjectNotFoundException(NON_EXISTENT_USER);
        return user.get();
    }
}
