package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.ProductMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ProductReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper = ProductMapper.INSTANCE;

    private final ProductRepository repository;

    private final FileStorageService fileStorageService;

    public Long save(ProductReqDto productReqDto, MultipartFile file){
        Product productConverted = mapper.ProductReqDtoToEntity(productReqDto);
        if(productConverted.getAvailable() == null)
            productConverted.setAvailable(true);
        if(productConverted.getHighlight() == null)
            productConverted.setHighlight(false);
        String imgUrl = fileStorageService.store(file);
        productConverted.setImgUrl(imgUrl);
        return repository.save(productConverted).getId();
    }

}
