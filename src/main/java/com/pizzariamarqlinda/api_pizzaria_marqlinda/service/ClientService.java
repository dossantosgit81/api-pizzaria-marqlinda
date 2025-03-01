package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectAlreadyExists;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.ClientMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Client;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ClientDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    @Autowired
    @Setter
    private PasswordEncoder encoder;
    private final ClientMapper mapper;

    public void save(ClientDto clientDto){
        validateUserExistsId(clientDto.clientId());
        var client = mapper.clientDtoToEntity(clientDto);
        String password = client.getClientSecret();
        client.setClientSecret(encoder.encode(password));
        repository.save(client);
    }

    private void validateUserExistsId(String clientId){
        Optional<Client> clientFound = repository.findByClientId(clientId);
        if(clientFound.isPresent())
            throw new ObjectAlreadyExists("Client já está cadastrado.");
    }

    public Client findById(String clientId){
        Optional<Client> client = repository.findByClientId(clientId);
        return client.orElse(null);
    }

}
