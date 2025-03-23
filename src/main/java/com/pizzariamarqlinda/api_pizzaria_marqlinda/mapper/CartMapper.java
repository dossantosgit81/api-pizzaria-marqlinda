package com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Cart;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Category;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CartResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CategoryResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartResDto entityToCartResDto (Cart cart);

}
