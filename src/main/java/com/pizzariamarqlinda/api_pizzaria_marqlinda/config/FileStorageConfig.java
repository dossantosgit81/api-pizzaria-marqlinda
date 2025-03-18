package com.pizzariamarqlinda.api_pizzaria_marqlinda.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;



@Getter
@Setter
@Configuration
public class FileStorageConfig {

    @Value("${file.upload-dir}")
    private String uploadDir;
}
