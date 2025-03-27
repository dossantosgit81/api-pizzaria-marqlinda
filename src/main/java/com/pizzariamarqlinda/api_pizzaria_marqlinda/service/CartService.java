package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.CartMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.ItemProduct;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CartResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ItemCartReqDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper mapper = CartMapper.INSTANCE;
    private final ProductService productService;
    private final LoggedUserService loggedUserService;
    private final ItemProductService itemCartService;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public CartResDto add(JwtAuthenticationToken token, ItemCartReqDto itemCartReqDto, Long idProduct){
        User loggedUser = loggedUserService.loggedUser(token);
        Product product = productService.findById(idProduct);
        ItemProduct itemCart = itemCartService.save(loggedUser, idProduct, itemCartReqDto, product);
        entityManager.flush();
        entityManager.refresh(loggedUser);
        return mapper.entityToCartResDto(itemCart.getCart());
    }

    @Transactional
    public CartResDto deleteItemCart(JwtAuthenticationToken token, Long idItemCart){
        User user = loggedUserService.loggedUser(token);
        Set<ItemProduct> items = user.getCart().getItems();
        Optional<ItemProduct> itemCart = items.stream().filter(item -> item.getId().equals(idItemCart)).findFirst();
        if(itemCart.isPresent()){
            items.remove(itemCart.get());
            return mapper.entityToCartResDto(user.getCart());
        }
        throw new ObjectNotFoundException("Item não encontrado. "+idItemCart);
    }

    public int getQuantityItemsCart(JwtAuthenticationToken token){
        User user = loggedUserService.loggedUser(token);
        return Optional
                .ofNullable(user.getCart())
                .map(cart -> cart.getItems().size())
                .orElse(0);
    }

}
