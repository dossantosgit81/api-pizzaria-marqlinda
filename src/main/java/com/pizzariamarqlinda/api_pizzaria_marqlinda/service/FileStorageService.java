package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.FileStorageConfig;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.FileStorageException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.WrapFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path location;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig){
        this.location = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.location);
        }catch (IOException e){
            throw new FileStorageException("Não foi possivel criar o diretório do arquivo.");
        }
    }

    public WrapFile store(MultipartFile file){
        if("".equals(file.getOriginalFilename())){
            throw new FileStorageException("Nome de arquivo inválido.");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")){
                throw new FileStorageException("O arquivo contém caracteres inválidos.");
            }
            Path targetLocation = this.location.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return new WrapFile(fileName, targetLocation.toString());
        }catch (IOException e){
            throw new FileStorageException("Não foi possivel armazenar o arquivo. Tente novamente.");
        }
    }
}


