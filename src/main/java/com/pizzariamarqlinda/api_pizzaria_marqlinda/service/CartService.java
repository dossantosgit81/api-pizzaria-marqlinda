package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.ItemCart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ItemCartReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository repository;
    private final UserService userService;
    private final ProductService productService;
    private final ValidatorLoggedUserService loggedUserService;
    private final ItemCartService itemCartService;

    //TODO Já retorna o objeto adicionado para o usuário
    @Transactional
    public void add(JwtAuthenticationToken token, ItemCartReqDto itemCartReqDto, Long idProduct){
        User loggedUser = loggedUserService.loggedUser(token);
        Product product = productService.findById(idProduct);
        ItemCart itemCart = new ItemCart();
        itemCart.setProduct(product);
        itemCart.setQuantity(itemCartReqDto.quantity());
        itemCart.setCart(loggedUser.getCart());
        itemCartService.save(itemCart);
        Cart cart = loggedUserService.loggedUser(token).getCart();
        //return cartDto
    }
}
