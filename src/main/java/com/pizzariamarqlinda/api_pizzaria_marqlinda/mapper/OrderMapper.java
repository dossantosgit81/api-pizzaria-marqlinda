package com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Address;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Order;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.*;
import org.aspectj.weaver.ast.Or;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order orderReqDtoToEntity(OrderReqDto orderReqDto);
    OrderResDto entityToOrderResDto(Order order);
    OrderResUpdateDto entityToOrderResUpdateDto(Order order);

}
