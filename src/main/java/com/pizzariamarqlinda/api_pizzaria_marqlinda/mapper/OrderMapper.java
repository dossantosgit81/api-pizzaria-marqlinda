package com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Address;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Order;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order orderReqDtoToEntity(OrderReqDto orderReqDto);
    OrderResDto entityToOrderResDto(Order order);

}
