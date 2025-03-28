package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.*;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.CategoryService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
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

@RequestMapping("/api/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_USER')")
    public ResponseEntity<Void> create(@Valid @RequestBody CategoryReqDto categoryReqDto, UriComponentsBuilder uriBuilder){
        service.save(categoryReqDto);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryResDto>> findAll(){
        var res = service.all();
        return ResponseEntity.ok(res);
    }

    @PutMapping(value = "{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_USER')")
    public ResponseEntity<UserResDto> update( @Valid @RequestBody CategoryReqDto categoryReqDto, @PathVariable Long id){
        service.update(id, categoryReqDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_USER')")
    public ResponseEntity<Resource> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
