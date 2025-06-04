package com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.ItemProduct;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ItemProductResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemProductMapper {

    ItemProductMapper INSTANCE = Mappers.getMapper(ItemProductMapper.class);

    ItemProductResDto entityToResDto(ItemProduct itemProduct);
}
