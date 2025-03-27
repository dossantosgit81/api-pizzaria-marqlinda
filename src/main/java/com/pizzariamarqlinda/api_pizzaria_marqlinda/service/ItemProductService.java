package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.ItemProduct;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ItemProductReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.ItemProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemProductService {

    private final ItemProductRepository repository;

    @Transactional
    public ItemProduct save(User user, Long idProduct, ItemProductReqDto itemCartReqDto, Product product){
        Optional<ItemProduct> itemCart = user.getCart().getItems().stream().filter(item -> item.getProduct().getId().equals(idProduct)).findFirst();
        if(itemCart.isPresent()){
            ItemProduct itemUpdated = itemCart.get();
            itemUpdated.setQuantity(itemUpdated.getQuantity()+itemCartReqDto.quantity());
            itemUpdated.setSubtotal();
            return repository.save(itemUpdated);
        }else {
            ItemProduct newItem = new ItemProduct();
            newItem.setProduct(product);
            newItem.setQuantity(itemCartReqDto.quantity());
            newItem.setCart(user.getCart());
            newItem.setSubtotal();
            return repository.save(newItem);
        }
    }

    public List<ItemProduct> all(Cart cart){
       return repository.findAllByCartId(cart.getId());
    }

    public ItemProduct findById(Long idItemCart){
        return repository.findById(idItemCart).orElseThrow(() -> new ObjectNotFoundException("Item não encontrado."));
    }

}
