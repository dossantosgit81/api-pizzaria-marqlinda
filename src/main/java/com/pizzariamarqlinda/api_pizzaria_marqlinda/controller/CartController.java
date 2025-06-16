package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CartResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ItemProductReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/carts")
@RestController
@RequiredArgsConstructor
@Tag(name =  "Cart")
public class CartController {

    private final CartService service;

    @PostMapping(value = "/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartResDto> create(@RequestBody ItemProductReqDto itemCartReqDto, @PathVariable("id") Long id, JwtAuthenticationToken token){
        var res= service.add(token, itemCartReqDto, id);
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<CartResDto> delete (@PathVariable Long id, JwtAuthenticationToken token){
        var res = service.deleteItemCart(token, id);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCountItems(JwtAuthenticationToken token){
        return ResponseEntity.ok(service.getQuantityItemsCart(token));
    }

    @GetMapping("/products")
    public ResponseEntity<CartResDto> all(JwtAuthenticationToken token){
        var res = service.all(token);
        return ResponseEntity.ok(res);
    }
}
