package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ProductReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    private final Validator validator;

    @PostMapping
    public ResponseEntity<List<String>> create(@ModelAttribute ProductReqDto product,
                                       @RequestParam MultipartFile file,
                                       UriComponentsBuilder uriBuilder){
        Set<ConstraintViolation<ProductReqDto>> violations = validator.validate(product);
        if(!violations.isEmpty()){
            var errors = violations.stream().map(ConstraintViolation::getMessage).toList();
            return ResponseEntity.unprocessableEntity().body(errors);
        }

        Long idProduct = service.save(product, file);
        URI uri = uriBuilder.path("/api/products/{id}").buildAndExpand(idProduct).toUri();
        return ResponseEntity.created(uri).body(null);
    }
}
