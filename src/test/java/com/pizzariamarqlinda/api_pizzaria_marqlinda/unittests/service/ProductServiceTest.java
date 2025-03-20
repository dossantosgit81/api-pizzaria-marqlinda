package com.pizzariamarqlinda.api_pizzaria_marqlinda.unittests.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.FileStorageConfig;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.FileStorageException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.InvalidFormatImageException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Product;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.ProductReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.WrapFile;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.ProductRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.FileStorageService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private FileStorageService fileStorageService;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Test
    public void shouldReturnIdProduct_WhenProductValidFields(){
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-image.jpeg",
                "image/jpeg",
                "content".getBytes()
        );
        ProductReqDto productReqDto = new ProductReqDto(
                "Pizza de calabresa",
                "Calabresa, queijo, azeitona",
                new BigDecimal("50.00"),
                null);
        var product = Product.builder().id(1L).build();
        doReturn(new WrapFile("fake", "fake")).when(fileStorageService).store(file);
        doReturn(product).when(repository).save(productArgumentCaptor.capture());

        var result = service.save(productReqDto, file);
        assertEquals(Long.class, result.getClass());
        verify(repository).save(any(Product.class));
    }

}
