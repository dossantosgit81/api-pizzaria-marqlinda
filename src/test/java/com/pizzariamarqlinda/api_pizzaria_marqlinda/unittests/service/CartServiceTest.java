package com.pizzariamarqlinda.api_pizzaria_marqlinda.unittests.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.CartMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.ItemCart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CartResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ItemCartReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.CartService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.ItemCartService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.LoggedUserService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartMapper cartMapper;

    @Mock
    private ProductService productService;

    @Mock
    private LoggedUserService loggedUserService;

    @Mock
    private ItemCartService itemCartService;

    @InjectMocks
    private CartService service;

    User user;
    ItemCartReqDto itemCartReqDto;
    Product product;
    ItemCart itemCart;

    @BeforeEach
    public void setUp(){
        user = User.builder().id(1L).cart(new Cart()).build();
        itemCartReqDto = new ItemCartReqDto(3);
        product = Product.builder().id(1L).description("Pizza of banana").build();
        itemCart = ItemCart.builder().id(1L).build();
    }

    @Test
    public void shouldRegisterItemInCart_WhenValidItems(){
        JwtAuthenticationToken token = mock(JwtAuthenticationToken.class);
        doReturn(user).when(loggedUserService).loggedUser(token);
        doReturn(product).when(productService).findById(anyLong());
        var res= service.add(token, itemCartReqDto, 1L);
       // doReturn()
        assertEquals(CartResDto.class, res.getClass());
    }


}
