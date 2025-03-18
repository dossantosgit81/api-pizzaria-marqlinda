package com.pizzariamarqlinda.api_pizzaria_marqlinda.unittests.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.FileStorageConfig;
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

    @BeforeEach
    public void setUp(){
        FileStorageConfig fileStorageConfig = new FileStorageConfig();
        fileStorageConfig.setUploadDir("/home/rafael/Documents/storage");
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
        Assertions.assertEquals(String.class, result.getClass());
        Assertions.assertTrue(result.endsWith("test-image.jpeg"));
    }

}
