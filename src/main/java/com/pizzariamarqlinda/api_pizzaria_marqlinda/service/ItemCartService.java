package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.ItemCart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ItemCartReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.CartRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.ItemCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCartService {

    private final ItemCartRepository repository;

    public void save(ItemCart itemCart){
        repository.save(itemCart);
    }
}
