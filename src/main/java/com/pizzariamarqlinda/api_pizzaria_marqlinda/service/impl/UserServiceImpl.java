package com.pizzariamarqlinda.api_pizzaria_marqlinda.service.impl;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.UserMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.UserRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper mapper = UserMapper.INSTANCE;
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Transactional
    @Override
    public Long save(UserReqDto user) {
      //  repository.findByLogin(user.getEmail());
        User userConverted = mapper.userReqDtoToEntity(user);
        String password = user.getPassword();
        userConverted.setPassword(encoder.encode(password));
        userConverted.setRoles(List.of("COMMON_USER"));
        userConverted.setCart(new Cart());
        User savedUser = repository.save(userConverted);
        return savedUser.getId();
    }

    @Override
    public List<UserResDto> all() {
       return repository.findAll().stream()
                .map(mapper::entityToUserResDto)
                .toList();
    }


}
