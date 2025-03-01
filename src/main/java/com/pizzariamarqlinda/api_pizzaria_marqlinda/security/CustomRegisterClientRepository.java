package com.pizzariamarqlinda.api_pizzaria_marqlinda.security;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Client;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.ClientService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomRegisterClientRepository implements RegisteredClientRepository {

    private final ClientService service;

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = service.findById(clientId);
        if(client == null)
            return null;
        return RegisteredClient.withId(client.getId().toString())
                .clientId(client.getClientId())
                .scope(client.getScope())
                .redirectUri(client.getRedirectURI())
                .clientSecret(client.getClientSecret())
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .build();
    }
}
