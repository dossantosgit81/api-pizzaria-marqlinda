package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
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

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody UserReqDto userReqDto, UriComponentsBuilder uriBuilder){
        Long idUser = service.save(userReqDto);
        URI uri = uriBuilder.path("/api/users/{id}").buildAndExpand(idUser).toUri();
        return ResponseEntity.created(uri).body(null);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_USER')")
    public ResponseEntity<List<UserResDto>> findAll(){
        return ResponseEntity.ok(service.all());
    }

    @GetMapping(value = "{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResDto> findById(@PathVariable("id") Long id, JwtAuthenticationToken token){
        UserResDto user = service.findById(id, token);
        return ResponseEntity.ok(user);
    }
}
