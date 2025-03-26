package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CategoryReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequestMapping("/api/addresses")
@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody AddressReqDto addressReqDto, JwtAuthenticationToken token, UriComponentsBuilder uriBuilder){
        var idAddress= service.save(addressReqDto, token);
        URI uri = uriBuilder.path("/api/addresses/{id}").buildAndExpand(idAddress).toUri();
        return ResponseEntity.created(uri).body(null);
    }

    @PutMapping(value = "{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AddressResDto> update(@Valid @RequestBody AddressReqDto addressReqDto, @PathVariable("id") Long id, JwtAuthenticationToken token){
        var res = service.update(addressReqDto, id, token);
        return ResponseEntity.ok(res);
    }

//    @DeleteMapping("/products/{id}")
//    public ResponseEntity<CartResDto> delete (@PathVariable Long id, JwtAuthenticationToken token){
//        var res = service.deleteItemCart(token, id);
//        return ResponseEntity.ok().body(res);
//    }

//    @GetMapping("/count")
//    public ResponseEntity<Integer> getCountItems(JwtAuthenticationToken token){
//        return ResponseEntity.ok(service.getQuantityItemsCart(token));
//    }
}
