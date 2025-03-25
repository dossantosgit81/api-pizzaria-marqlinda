package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.ItemCart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ItemCartReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.ItemCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemCartService {

    private final ItemCartRepository repository;

    @Transactional
    public ItemCart save(User user, Long idProduct, ItemCartReqDto itemCartReqDto, Product product){
        Optional<ItemCart> itemCart = user.getCart().getItems().stream().filter(item -> item.getProduct().getId().equals(idProduct)).findFirst();
        if(itemCart.isPresent()){
            ItemCart itemUpdated = itemCart.get();
            itemUpdated.setQuantity(itemUpdated.getQuantity()+itemCartReqDto.quantity());
            itemUpdated.setSubtotal();
            return repository.save(itemUpdated);
        }else {
            ItemCart newItem = new ItemCart();
            newItem.setProduct(product);
            newItem.setQuantity(itemCartReqDto.quantity());
            newItem.setCart(user.getCart());
            newItem.setSubtotal();
            return repository.save(newItem);
        }
    }

    public List<ItemCart> all(Cart cart){
       return repository.findAllByCartId(cart.getId());
    }

    public ItemCart findById(Long idItemCart){
        return repository.findById(idItemCart).orElseThrow(() -> new ObjectNotFoundException("Item não encontrado."));
    }

}
