package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RoleDto;
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
@PreAuthorize("hasRole('ADMIN_USER')")
public class RoleController {

    private final RoleService service;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody RoleDto roleDto, UriComponentsBuilder uriBuilder){
        Long idRole = service.save(roleDto);
        URI uri = uriBuilder.path("/api/roles/{id}").buildAndExpand(idRole).toUri();
        return ResponseEntity.created(uri).body(null);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoleDto>> findAll(){
        return ResponseEntity.ok(service.all());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> findAll(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
