package com.pizzariamarqlinda.api_pizzaria_marqlinda.unittests.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.FileStorageConfig;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.FileStorageException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.WrapFile;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.FileStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class FileStorageServiceTest {

    FileStorageService fileStorageService;
    FileStorageConfig fileStorageConfig;

    @BeforeEach
    public void setUp(){
        fileStorageConfig = new FileStorageConfig();
        fileStorageConfig.setUploadDir("./src/test/resources/storage");
        fileStorageService = new FileStorageService(fileStorageConfig);
    }

    @Test
    public void shouldWriteFileInDisk_WhenValidFile(){
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-image.jpeg",
                "image/jpeg",
                "content".getBytes()
        );
        var result = fileStorageService.store(file);
        Assertions.assertEquals(WrapFile.class, result.getClass());
        Assertions.assertEquals("test-image.jpeg", result.getFileOriginalName());
        Assertions.assertTrue(result.getImgUrl().endsWith("test-image.jpeg"));
    }

    @Test
    public void shouldReturnFileStorageException_WhenFileNameNull(){
        MockMultipartFile file = new MockMultipartFile(
                "file",
                null,
                "image/jpeg",
                "content".getBytes()
        );
        Assertions.assertThrows(FileStorageException.class, ()->{
            fileStorageService.store(file);
        });
    }

    @Test
    public void shouldReturnFileStorageException_WhenFileHasInvalidCharacter(){
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "../null",
                "image/jpeg",
                "content".getBytes()
        );
        Assertions.assertThrows(FileStorageException.class, ()->{
            fileStorageService.store(file);
        });
    }

}
