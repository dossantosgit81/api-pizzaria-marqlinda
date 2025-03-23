package com.pizzariamarqlinda.api_pizzaria_marqlinda.unittests.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.CartMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.ItemCartService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.LoggedUserService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.ProductService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartMapper cartMapper;

    @Mock
    private ProductService productService;

    @Mock
    private LoggedUserService loggedUserService;

    @InjectMocks
    private ItemCartService service;



}
