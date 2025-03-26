package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.AddressMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Address;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    public static final String ADDRESS_IS_NOT_FOUND = "Endereço não foi encontrado.";
    private final AddressMapper mapper = AddressMapper.INSTANCE;
    private final AddressRepository repository;
    private final LoggedUserService loggedUserService;

    public Long save(AddressReqDto addressReqDto, JwtAuthenticationToken token){
        User user = loggedUserService.loggedUser(token);
        Address addressConverted = mapper.addressReqDtoToEntity(addressReqDto);
        addressConverted.setUser(user);
        return repository.save(addressConverted).getId();
    }

    public AddressResDto update(AddressReqDto addressReqDto, Long idAddress, JwtAuthenticationToken token){
        User user = loggedUserService.loggedUser(token);
        Optional<Address> addressSearched = repository.findByUser(idAddress, user.getId());
        if(addressSearched.isEmpty())
            throw new ObjectNotFoundException(ADDRESS_IS_NOT_FOUND);
        Address entity = mapper.addressReqDtoToEntity(addressReqDto);
        return mapper.entityToAddressResDto(repository.save(entity));
    }

    public List<AddressResDto> all(JwtAuthenticationToken token){
        User user = loggedUserService.loggedUser(token);
        return user.getAddresses().stream().map(mapper::entityToAddressResDto).toList();
    }

    public void delete(JwtAuthenticationToken token, Long idAddress){
        User user = loggedUserService.loggedUser(token);
        List<Address> addresses = user.getAddresses();
        Optional<Address> addressSearched = addresses.stream()
                .filter(address -> address.getId().equals(idAddress))
                .findFirst();
        addressSearched.ifPresent(addresses::remove);
        throw new ObjectNotFoundException(ADDRESS_IS_NOT_FOUND);
    }
}
