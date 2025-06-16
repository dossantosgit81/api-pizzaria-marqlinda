package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.*;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/addresses")
@RestController
@RequiredArgsConstructor
@Tag(name =  "Addresses")
public class AddressController {

    private final AddressService service;

    @Operation(summary = "Create", description = "Create new address")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody AddressReqDto addressReqDto, JwtAuthenticationToken token, UriComponentsBuilder uriBuilder){
        service.save(addressReqDto, token);
        return ResponseEntity.ok(null);
    }

    @PutMapping(value = "{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AddressResDto> update(@Valid @RequestBody AddressReqDto addressReqDto, @PathVariable("id") Long id, JwtAuthenticationToken token){
        var res = service.update(addressReqDto, id, token);
        return ResponseEntity.ok(res);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressResDto>> findAll(JwtAuthenticationToken token){
        var res = service.all(token);
        return ResponseEntity.ok().body(res);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Long id, JwtAuthenticationToken token){
        service.delete(token, id);
        return ResponseEntity.noContent().build();
    }

}
