package com.pizzariamarqlinda.api_pizzaria_marqlinda.resource;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RoleDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/roles")
@RestController
@RequiredArgsConstructor
public class RoleResource {

    private final RoleService service;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody RoleDto roleDto, UriComponentsBuilder uriBuilder){
        Long idRole = service.Save(roleDto);
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
