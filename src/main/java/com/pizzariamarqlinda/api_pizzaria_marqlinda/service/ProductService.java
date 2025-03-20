package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.ProductMapper;
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

    public Long save(ProductReqDto productReqDto, MultipartFile file){
        Product productConverted = mapper.productReqDtoToEntity(productReqDto);
        WrapFile objectWrapFile = fileStorageService.store(file);
        String imgUrl = objectWrapFile.getImgUrl();
        String fileName = objectWrapFile.getFileOriginalName();
        productConverted.setImgUrl(imgUrl);
        productConverted.setFileName(fileName);
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

}
