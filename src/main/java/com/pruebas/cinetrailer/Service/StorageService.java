/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebas.cinetrailer.Service;

import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author aimer
 */
public interface StorageService {

    void init();

    String storage(MultipartFile file, String titulo);

    Path load(String filename);

    Resource loadAsResource(String filename);

    void delete(String filename);

}
