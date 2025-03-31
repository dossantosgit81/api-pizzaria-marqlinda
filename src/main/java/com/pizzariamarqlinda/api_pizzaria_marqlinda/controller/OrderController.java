package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Order;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> findAll(JwtAuthenticationToken token){
        service.setToken(token);
        var res = service.all();
        return ResponseEntity.ok(res);
    }

}
