package com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.PaymentMethod;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.PaymentMethodDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMethodMapper {

    PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);
    PaymentMethod paymentMethodDtoToEntity(PaymentMethodDto paymentMethodDto);
    PaymentMethodDto entityToPaymentMethodDto(PaymentMethod paymentMethod);

}
