package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody OrderReqDto orderReqDto, UriComponentsBuilder uriBuilder, JwtAuthenticationToken token){
        service.setToken(token);
        Long idOrder = service.save(orderReqDto);
        URI uri = uriBuilder.path("/api/orders/{id}").buildAndExpand(idOrder).toUri();
        return ResponseEntity.created(uri).body(null);
    }

}
