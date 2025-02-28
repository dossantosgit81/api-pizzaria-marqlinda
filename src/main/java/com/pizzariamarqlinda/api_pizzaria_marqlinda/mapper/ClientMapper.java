package com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Client;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ClientDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client clientDtoToEntity(ClientDto clientDto);
}
