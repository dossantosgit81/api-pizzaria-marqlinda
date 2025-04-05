package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.ProductMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Category;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ProductReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ProductResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.WrapFile;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper = ProductMapper.INSTANCE;

    private final ProductRepository repository;
    private final FileStorageService fileStorageService;
    private final CategoryService categoryService;

    public Long save(ProductReqDto productReqDto, Long idCategory, MultipartFile file){
        var category = categoryService.findById(idCategory);
        Product productConverted = mapper.productReqDtoToEntity(productReqDto);
        WrapFile objectWrapFile = fileStorageService.store(file);
        String imgUrl = objectWrapFile.getImgUrl();
        String fileName = objectWrapFile.getFileOriginalName();
        productConverted.setImgUrl(imgUrl);
        productConverted.setFileName(fileName);
        productConverted.setCategory(category);
        if(productReqDto.highlight() == null)
            productConverted.setHighlight(false);
        return repository.save(productConverted).getId();
    }

    public void delete(Long id){
        var product = repository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Produto não encontrado."));
        repository.delete(product);
    }

    public Resource getFile(Long id){
       Optional<Product> productSearched = repository.findById(id);
       if(productSearched.isPresent()){
           return fileStorageService.getFile(productSearched.get().getImgUrl());
       }
       throw new ObjectNotFoundException("Produto não encontrado.");
    }

    public Page<ProductResDto> all(Pageable pageable){
        var products = repository.findAll(pageable);
        return products.map(mapper::entityToProductResDto);
    }

    public Page<ProductResDto> findByCategory(Long idCategory, Pageable pageable){
        Category category = categoryService.findById(idCategory);
        var products = repository.findByCategory(category.getId(), pageable);
        return products.map(mapper::entityToProductResDto);
    }

    public Product findById(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado."));
    }

}
