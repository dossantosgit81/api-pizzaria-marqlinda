package com.pizzariamarqlinda.api_pizzaria_marqlinda.service.impl;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectAlreadyExists;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.UserMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper mapper = UserMapper.INSTANCE;
    private final UserRepository repository;
    @Setter
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Long save(UserReqDto user) {
        this.validateLogin(user.getEmail());
        User userConverted = convertedUser(user);
        User savedUser = repository.save(userConverted);
        return savedUser.getId();
    }

    private User convertedUser(UserReqDto userReq){
        User userConverted = mapper.userReqDtoToEntity(userReq);
        String password = userReq.getPassword();
        userConverted.setPassword(encoder.encode(password));
        userConverted.setRoles(List.of("COMMON_USER"));
        userConverted.setCart(new Cart());
        return userConverted;
    }

    private void validateLogin(String email){
        if(repository.findByEmail(email).isPresent())
            throw new ObjectAlreadyExists("Email já existe.");
    }

    @Override
    public List<UserResDto> all() {
       return repository.findAll().stream()
                .map(mapper::entityToUserResDto)
                .toList();
    }


}
