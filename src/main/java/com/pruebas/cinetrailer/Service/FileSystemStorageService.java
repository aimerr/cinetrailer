/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebas.cinetrailer.Service;

import com.pruebas.cinetrailer.exception.FileNotFoundException;
import com.pruebas.cinetrailer.exception.StorageException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author aimer
 */
@Service
public class FileSystemStorageService implements StorageService {

    @Value("${storage.location}")
    private String storageLocation;

    @PostConstruct
    @Override
    public void init() {

        try {
            Files.createDirectories(Paths.get(storageLocation));
        } catch (IOException ex) {
            throw new StorageException("Error al inicializar la ubicacion del almacen de archivos.");
        }
    }

    @Override
    public String storage(MultipartFile file,String titulo) {

        String filename = titulo+"_"+file.getOriginalFilename();
        if (file.isEmpty()) {
            throw new StorageException("No se puede almacenar un archivo vacio.");
        }

        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, Paths.get(storageLocation).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new StorageException("Error al almacenar el archivo " + filename, ex);
        }

        return filename;
    }

    @Override
    public Path load(String filename) {

        return Paths.get(storageLocation).resolve(filename);

    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                 throw new FileNotFoundException("No se pudo encontrar el archivo " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("No se pudo encontrar el archivo " + filename, ex);
        }

    }

    @Override
    public void delete(String filename) {
    
        Path file = load(filename);
        try {
            FileSystemUtils.deleteRecursively(file);
        } catch (IOException e) {
            
        }
    
    }

}
