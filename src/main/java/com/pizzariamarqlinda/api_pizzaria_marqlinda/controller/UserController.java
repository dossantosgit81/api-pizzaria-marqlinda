package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.PageResponseDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserUpdateRoleReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
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
    public ResponseEntity<PageResponseDto<UserResDto>> findAll(@RequestParam int pageNumber,
                                                    @RequestParam int pageSize,
                                                    @RequestParam(defaultValue = "id") String sortBy,
                                                    @RequestParam(defaultValue = "ASC") String direction){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        var res = service.all(PageRequest.of(pageNumber, pageSize, sort));
        return ResponseEntity.ok(new PageResponseDto<>(res));
    }

    @GetMapping(value = "{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResDto> findById(@PathVariable("id") Long id, JwtAuthenticationToken token){
        service.setToken(token);
        UserResDto user = service.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/search/{email}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResDto> findByEmail(@PathVariable("email") String email, JwtAuthenticationToken token){
        service.setToken(token);
        UserResDto user = service.findByEmailWithValidationsPermission(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/roles")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_USER')")
    public ResponseEntity<UserResDto> updateRolesUser(@RequestParam("email") String email,
                                                      @RequestBody UserUpdateRoleReqDto userUpdateRoleReqDto,
                                                      JwtAuthenticationToken token){
        service.setToken(token);
        var result = service.updateRolesUserId(email, userUpdateRoleReqDto);
        return ResponseEntity.ok(result);
    }
}
