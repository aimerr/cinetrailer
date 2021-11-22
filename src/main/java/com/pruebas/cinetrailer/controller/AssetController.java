/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pruebas.cinetrailer.controller;

import com.pruebas.cinetrailer.Service.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aimer
 */
@RestController
@RequestMapping("/assets")
public class AssetController {
    
    @Autowired
    private FileSystemStorageService fileSystemStorageService;
    
    @GetMapping("/{filename:.+}")
    Resource getResource(@PathVariable("filename") String filename){
    
        return fileSystemStorageService.loadAsResource(filename);
    }
    
}
