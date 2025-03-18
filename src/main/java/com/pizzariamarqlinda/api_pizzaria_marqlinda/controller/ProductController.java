package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ProductReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @ModelAttribute ProductReqDto product,
                                       @RequestParam MultipartFile file,
                                       UriComponentsBuilder uriBuilder){
        Long idProduct = service.save(product, file);
        URI uri = uriBuilder.path("/api/products/{id}").buildAndExpand(idProduct).toUri();
        return ResponseEntity.created(uri).body(null);
    }
}
