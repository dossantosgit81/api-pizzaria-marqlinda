package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ProductReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('SCOPE_ADMIN_USER')")
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

    @GetMapping("{id}/image")
    public ResponseEntity<Resource> file (@PathVariable Long id){
        Resource image = service.getFile(id);
        if(image.exists() && image.isReadable()){
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+image.getFilename() + "\"")
                    .body(image);
        }
        return ResponseEntity.internalServerError().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Resource> delete (@PathVariable Long id){
         service.delete(id);
         return ResponseEntity.noContent().build();
    }
}
