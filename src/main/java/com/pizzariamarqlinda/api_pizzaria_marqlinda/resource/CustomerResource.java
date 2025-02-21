package com.pizzariamarqlinda.api_pizzaria_marqlinda.resource;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/customers")
@RestController
@RequiredArgsConstructor
public class CustomerResource {

    private final UserService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody UserReqDto userReqDto, UriComponentsBuilder uriBuilder){
        Long idUser = service.save(userReqDto);
        URI uri = uriBuilder.path("/customers/{id}").buildAndExpand(idUser).toUri();
        return ResponseEntity.created(uri).body(null);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResDto>> findAll(){
        return ResponseEntity.ok(service.all());
    }
}
