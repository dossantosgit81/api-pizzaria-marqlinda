package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerResDto;

import java.util.List;

public interface CustomerService {
    Long save (CustomerReqDto customer);
    List<CustomerResDto> all ();
}
