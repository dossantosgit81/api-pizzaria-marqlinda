package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CartResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ItemCartReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/carts")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @PostMapping(value = "/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartResDto> create(@RequestBody ItemCartReqDto itemCartReqDto, @PathVariable("id") Long id, JwtAuthenticationToken token){
        var res= service.add(token, itemCartReqDto, id);
        return ResponseEntity.ok().body(res);
    }
}
