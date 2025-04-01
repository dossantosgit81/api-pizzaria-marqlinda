package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.PageResponseDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/api/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResDto> create(@Valid @RequestBody OrderReqDto orderReqDto, UriComponentsBuilder uriBuilder, JwtAuthenticationToken token){
        service.setToken(token);
        OrderResDto orderResDto = service.save(orderReqDto);
        return ResponseEntity.ok(orderResDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDto<OrderResDto>> findAll(JwtAuthenticationToken token, @RequestParam(defaultValue = "0") int pageNumber,
                                                                @RequestParam(defaultValue = "10") int pageSize,
                                                                @RequestParam(defaultValue = "id") String sortBy,
                                                                @RequestParam(defaultValue = "ASC") String direction){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        service.setToken(token);
        var res = service.all(PageRequest.of(pageNumber, pageSize, sort));
        return ResponseEntity.ok(new PageResponseDto<>(res));
    }

}
