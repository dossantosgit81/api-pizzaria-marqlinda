package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.ProductMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.WrapFile;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ProductReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper = ProductMapper.INSTANCE;

    private final ProductRepository repository;

    private final FileStorageService fileStorageService;

    public Long save(ProductReqDto productReqDto, MultipartFile file){
        Product productConverted = mapper.ProductReqDtoToEntity(productReqDto);
        WrapFile objectWrapFile = fileStorageService.store(file);
        String imgUrl = objectWrapFile.getImgUrl();
        String fileName = objectWrapFile.getFileOriginalName();
        productConverted.setImgUrl(imgUrl);
        productConverted.setFileName(fileName);
        return repository.save(productConverted).getId();
    }

    public Resource getFile(Long id){
       Optional<Product> productSearched = repository.findById(1L);
       if(productSearched.isPresent()){
           return fileStorageService.getFile(productSearched.get().getImgUrl());
       }
       throw new ObjectNotFoundException("Produto não encontrado.");
    }

}
