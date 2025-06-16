package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.PaymentMethodDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.AddressService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.PaymentMethodService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/payments-methods")
@RestController
@RequiredArgsConstructor
@Tag(name =  "Payment Method")
public class PaymentMethodController {

    private final PaymentMethodService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_USER')")
    public ResponseEntity<Void> create(@Valid @RequestBody PaymentMethodDto paymentMethodDto, JwtAuthenticationToken token, UriComponentsBuilder uriBuilder){
        service.save(paymentMethodDto);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentMethodDto>> findAll(){
        var res = service.all();
        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_USER')")
    public ResponseEntity<Void> delete (@PathVariable("id") Long id, JwtAuthenticationToken token){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
