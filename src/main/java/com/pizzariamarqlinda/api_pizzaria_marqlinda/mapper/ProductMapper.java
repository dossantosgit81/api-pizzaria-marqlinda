package com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ProductReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    Product ProductReqDtoToEntity(ProductReqDto productReqDto);

}
