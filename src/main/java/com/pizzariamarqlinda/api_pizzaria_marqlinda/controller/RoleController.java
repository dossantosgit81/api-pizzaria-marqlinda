package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.RoleResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/roles")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_ADMIN_USER')")
@Tag(name =  "Role")
public class RoleController {

    private final RoleService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoleResDto>> findAll(){
        return ResponseEntity.ok(service.all());
    }

}
