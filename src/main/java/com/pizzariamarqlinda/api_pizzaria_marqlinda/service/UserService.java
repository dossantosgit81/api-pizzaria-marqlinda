package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    Long save (UserReqDto user);
    List<UserResDto> all ();
    User findByEmail(String email);

    UserResDto findById(Long id, Authentication authentication);
}
