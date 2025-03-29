package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderResDto;
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

@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResDto> create(@Valid @RequestBody OrderReqDto orderReqDto, UriComponentsBuilder uriBuilder, JwtAuthenticationToken token){
        service.setToken(token);
        OrderResDto orderResDto = service.save(orderReqDto);
        return ResponseEntity.ok(orderResDto);
    }

}
