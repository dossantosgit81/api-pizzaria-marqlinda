package com.pizzariamarqlinda.api_pizzaria_marqlinda.model.mapper;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Customer;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    Customer customerReqDtoToEntity(CustomerReqDto customer);
    CustomerResDto entityToCustomerResDto(Customer customer);

}
