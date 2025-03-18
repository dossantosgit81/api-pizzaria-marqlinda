package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.FileStorageConfig;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
        }catch (Exception e){
            throw new FileStorageException("Não foi possivel armazenar o arquivo.");
        }
    }

    public String store(MultipartFile file){
        if(file.getOriginalFilename() == null){
            throw new FileStorageException("Nome de arquivo inválido.");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")){
                throw new FileStorageException("O arquivo contém caracteres inválidos.");
            }
            Path targetLocation = this.location.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation.toString();
        }catch (Exception e){
            throw new FileStorageException("Não foi possivel armazenar o arquivo. Tente novamente.");
        }
    }

}
