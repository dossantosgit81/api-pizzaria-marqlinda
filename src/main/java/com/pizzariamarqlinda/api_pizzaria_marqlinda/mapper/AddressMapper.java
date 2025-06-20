package com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Address;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address addressReqDtoToEntity(AddressReqDto addressReqDto);
    AddressResDto entityToAddressResDto (Address address);

}
