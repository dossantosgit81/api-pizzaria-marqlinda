package com.pizzariamarqlinda.api_pizzaria_marqlinda.resource;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.CustomerResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.service.CustomerService;
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

    private final CustomerService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CustomerReqDto customerReqDto, UriComponentsBuilder uriBuilder){
        Long idCustomer = service.save(customerReqDto);
        URI uri = uriBuilder.path("/customers/{id}").buildAndExpand(idCustomer).toUri();
        return ResponseEntity.created(uri).body(null);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResDto>> findAll(){
        return ResponseEntity.ok(service.all());
    }
}
