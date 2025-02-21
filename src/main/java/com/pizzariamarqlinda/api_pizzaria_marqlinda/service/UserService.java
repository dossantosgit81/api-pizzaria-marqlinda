package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;

import java.util.List;

public interface UserService {
    Long save (UserReqDto user);
    List<UserResDto> all ();
}
