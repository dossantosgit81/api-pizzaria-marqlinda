package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.CartMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.ItemCart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CartResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ItemCartReqDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper mapper = CartMapper.INSTANCE;
    private final ProductService productService;
    private final LoggedUserService loggedUserService;
    private final ItemCartService itemCartService;

    @Transactional
    public CartResDto add(JwtAuthenticationToken token, ItemCartReqDto itemCartReqDto, Long idProduct){
        User loggedUser = loggedUserService.loggedUser(token);
        Product product = productService.findById(idProduct);
        ItemCart itemCart = new ItemCart();
        itemCart.setProduct(product);
        itemCart.setQuantity(itemCartReqDto.quantity());
        itemCart.setCart(loggedUser.getCart());
        itemCartService.save(itemCart);
        Cart cart = loggedUserService.loggedUser(token).getCart();
        return mapper.entityToCartResDto(cart);
    }

    @Transactional
    public CartResDto deleteItemCart(JwtAuthenticationToken token, Long idItemCart){
        User user = loggedUserService.loggedUser(token);
        Optional<ItemCart> itemCart = user.getCart().getItems().stream().filter(item -> item.getId().equals(idItemCart)).findFirst();
        if(itemCart.isPresent()){
            itemCartService.delete(itemCart.get());
            user.getCart().setItems(itemCartService.all(user.getCart()));
            return mapper.entityToCartResDto(user.getCart());
        }
        throw new ObjectNotFoundException("Item não encontrado. "+idItemCart);
    }

    public Integer getQuantityItemsCart(JwtAuthenticationToken token){
        User user = loggedUserService.loggedUser(token);
        return Optional
                .ofNullable(user.getCart())
                .map(cart -> cart.getItems().size())
                .orElse(0);
    }

}
