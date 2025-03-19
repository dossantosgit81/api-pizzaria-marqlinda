package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.ProductMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ProductReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.ProductRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper = ProductMapper.INSTANCE;

    private final ProductRepository repository;

    private final FileStorageService fileStorageService;

    private final Validator validator;

    public Long save(ProductReqDto productReqDto, MultipartFile file){
        Set<ConstraintViolation<ProductReqDto>> violations = validator.validate(productReqDto);
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
