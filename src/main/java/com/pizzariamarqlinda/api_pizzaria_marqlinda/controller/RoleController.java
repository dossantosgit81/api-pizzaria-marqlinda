package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RoleReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RolesResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/roles")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_ADMIN_USER')")
public class RoleController {

    private final RoleService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RolesResDto>> findAll(){
        return ResponseEntity.ok(service.all());
    }

}
