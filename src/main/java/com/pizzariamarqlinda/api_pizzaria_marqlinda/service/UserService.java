package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Long save (UserReqDto user);
    List<UserResDto> all ();
    Optional<User> findUserByEmail(String email);
}
