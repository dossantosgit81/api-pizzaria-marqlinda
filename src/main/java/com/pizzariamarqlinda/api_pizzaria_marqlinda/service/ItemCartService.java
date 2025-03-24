package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.ItemCart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.ItemCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemCartService {

    private final ItemCartRepository repository;

    public void save(ItemCart itemCart){
        repository.save(itemCart);
    }

    public void delete(ItemCart itemCart){
        repository.delete(itemCart);
    }

    public List<ItemCart> all(Cart cart){
       return repository.findAllByCartId(cart.getId());
    }
}
